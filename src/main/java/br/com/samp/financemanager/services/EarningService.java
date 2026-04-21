package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.EarningMapper;
import br.com.samp.financemanager.dto.request.EarningRequest;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.infrastructure.security.service.AuthenticatedUserService;
import br.com.samp.financemanager.model.Category;
import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import br.com.samp.financemanager.repository.CategoryRepository;
import br.com.samp.financemanager.repository.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.com.samp.financemanager.model.enums.CategoryType.EARNING;
import static br.com.samp.financemanager.model.enums.TransactionStatus.CONFIRMED;
import static br.com.samp.financemanager.model.enums.TransactionStatus.DELETED;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PENDING_CONFIRMATION;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PLANNED;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EarningMapper mapper;

    @Autowired
    private AuthenticatedUserService userAuthService;

    public List<EarningResponse> find(
            UUID categoryId,
            LocalDate date,
            TransactionStatus status
    ) {
        User user = userAuthService.getAuthenticatedUser();

        List<Earning> earnings = earningRepository.findWithFilters(user, categoryId, date, status);

        return  mapper.toResponseList(earnings);
    }

    public EarningResponse findByUUID(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Earning earning = earningRepository.findByUserAndUuid(user,id)
                .orElseThrow(()-> new ResourceNotFoundException("Earning not found"));

        return mapper.toResponse(earning);
    }

    public List<EarningResponse> findByCategoryUuid(UUID categoryId) {
        User user = userAuthService.getAuthenticatedUser();

        List<Earning> earnings = earningRepository.findByUserAndCategoriesUuid(user, categoryId);

        return  mapper.toResponseList(earnings);
    }

    public EarningResponse save(EarningRequest earningRequest) {
        User user = userAuthService.getAuthenticatedUser();

        List<Category> categories = getCategoriesByIds(earningRequest.categoriesIds());

        Earning earning = mapper.toEntity(earningRequest);
        earning.setUser(user);
        earning.getCategories().addAll(categories);
        earning = earningRepository.save(earning);

        return mapper.toResponse(earning);
    }

    public EarningResponse confirmEarning(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Earning earning = earningRepository.findByUserAndUuid(user,id)
                .orElseThrow(()-> new ResourceNotFoundException("Earning not found"));

        TransactionStatus status = earning.getStatus();
        if (status != PENDING_CONFIRMATION && status != PLANNED)
            throw new BusinessException("Earning not confirmed");

        earning.setStatus(CONFIRMED);
        earning = earningRepository.save(earning);
        return mapper.toResponse(earning);
    }

    public void deleteByUUID(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Earning earning = earningRepository.findByUserAndUuid(user,id)
                .orElseThrow(()-> new ResourceNotFoundException("Earning not found"));

        if (earning.getStatus() == DELETED)
            throw new BusinessException("Earning cannot be deleted");

        earning.setStatus(DELETED);
        earningRepository.save(earning);
    }

    private List<Category> getCategoriesByIds(List<UUID> categoriesIds) {
        List<Category> categories = categoryRepository.findAllByUuidIn(categoriesIds);

        if(categories.size()!= categoriesIds.size()){
            throw new ResourceNotFoundException("Category not found");
        }

        List<Category> invalidCategories = categories.stream()
                .filter(category -> category.getType()!= EARNING)
                .toList();

        if(!invalidCategories.isEmpty()){
            throw new BusinessException(
                    "Categories that are not related to earnings are not allowed.");
        }

        return categories;
    }

}
