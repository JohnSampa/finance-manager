package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.ExpenseMapper;
import br.com.samp.financemanager.dto.request.ExpenseRequest;
import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.model.Expense;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.ExpenseRepository;
import br.com.samp.financemanager.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.samp.financemanager.model.enums.ExpenseStatus.CONFIRMED;
import static br.com.samp.financemanager.model.enums.ExpenseStatus.DELETED;
import static br.com.samp.financemanager.model.enums.ExpenseStatus.PENDING_CONFIRMATION;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ExpenseMapper mapper;

    @Autowired
    private UserRepository userRepository;

    public List<ExpenseResponse> findByUserId(Long userId) {
        List<Expense> expenses = repository.findByUserId(userId);

        return mapper.toExpenseResponseList(expenses);
    }

    public ExpenseResponse findByUserIdAndId(Long userId,Long id) {
       Expense expense = repository.findByUserIdAndId(userId,id)
               .orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

       return mapper.toExpenseResponse(expense);
    }

    public ExpenseResponse saveExpense(Long userId,ExpenseRequest expenseRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + userId));

        Expense expense = mapper.toEntity(expenseRequest);

        expense.setUser(user);

        expense =  repository.save(expense);

        return mapper.toExpenseResponse(expense);
    }

    public ExpenseResponse confirmExpense(Long userId,Long expenseId) {
        Expense expense = repository.findByUserIdAndId(userId,expenseId)
                .orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

        if(expense.getStatus()!= PENDING_CONFIRMATION)
            throw new BusinessException("Expense cannot be confirmed");

        expense.setStatus(CONFIRMED);
        expense = repository.save(expense);

        return mapper.toExpenseResponse(expense);
    }

    public void deleteExpense(Long userId,Long expenseId) {
        Expense expense = repository.findByUserIdAndId(userId,expenseId)
                .orElseThrow(()-> new ResourceNotFoundException("Expense not found"));

        expense.setStatus(DELETED);
        expense = repository.save(expense);
    }



}
