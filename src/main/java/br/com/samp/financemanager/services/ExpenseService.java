package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.ExpenseMapper;
import br.com.samp.financemanager.dto.request.ExpenseRequest;
import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.infrastructure.security.service.AuthenticatedUserService;
import br.com.samp.financemanager.model.Category;
import br.com.samp.financemanager.model.Expense;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import br.com.samp.financemanager.repository.CategoryRepository;
import br.com.samp.financemanager.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.com.samp.financemanager.model.enums.CategoryType.EXPANSE;
import static br.com.samp.financemanager.model.enums.TransactionStatus.CONFIRMED;
import static br.com.samp.financemanager.model.enums.TransactionStatus.DELETED;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PENDING_CONFIRMATION;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PLANNED;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ExpenseMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthenticatedUserService userAuthService;

    public List<ExpenseResponse> find(
            Long categoryId,
            LocalDate date,
            TransactionStatus status
    ) {
        User user = userAuthService.getAuthenticatedUser();

        List<Expense> expenses = repository.findWithFilters(user,categoryId, date, status);

        return mapper.toExpenseResponseList(expenses);
    }

    public ExpenseResponse findById(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Expense expense = repository.findByUserAndUuid(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        return mapper.toExpenseResponse(expense);
    }

    public List<ExpenseResponse> findByCategoryUuid(UUID categoryId) {
        User user = userAuthService.getAuthenticatedUser();

        List<Expense> expenses = repository.findByUserAndCategoriesUuid(user, categoryId);

        return mapper.toExpenseResponseList(expenses);
    }

    public ExpenseResponse save(ExpenseRequest expenseRequest) {
        User user = userAuthService.getAuthenticatedUser();

        List<Category> categories = getCategoriesById(expenseRequest.categoryIds());

        Expense expense = mapper.toEntity(expenseRequest);
        expense.getCategories().addAll(categories);
        expense.setUser(user);

        expense = repository.save(expense);

        return mapper.toExpenseResponse(expense);
    }

    public ExpenseResponse confirmExpense(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Expense expense = repository.findByUserAndUuid(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        TransactionStatus expenseStatus = expense.getStatus();
        if (expenseStatus != PENDING_CONFIRMATION && expenseStatus != PLANNED)
            throw new BusinessException("Expense cannot be confirmed");

        expense.setStatus(CONFIRMED);
        expense = repository.save(expense);

        return mapper.toExpenseResponse(expense);
    }

    public void deleteById(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Expense expense = repository.findByUserAndUuid(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (expense.getStatus() == DELETED)
            throw new BusinessException("Expense cannot be deleted");

        expense.setStatus(DELETED);
        expense = repository.save(expense);
    }

    private List<Category> getCategoriesById(List<Long> ids) {

        List<Category> categories = categoryRepository.findAllById(ids);

        if (categories.size() != ids.size())
            throw new ResourceNotFoundException("Category not found");

        List<Category> invalidCategories = categories.stream()
                .filter(category -> category.getType()!= EXPANSE)
                .toList();

        if (!invalidCategories.isEmpty()) {
            throw new BusinessException(
                    "Categories that are not related to expenses are not allowed.");
        }

        return categories;
    }
}
