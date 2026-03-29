package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.EarningMapper;
import br.com.samp.financemanager.dto.request.EarningRequest;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.model.Category;
import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.enums.CategoryType;
import br.com.samp.financemanager.repository.CategoryRepository;
import br.com.samp.financemanager.repository.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.samp.financemanager.model.enums.CategoryType.EARNING;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EarningMapper mapper;

    public List<EarningResponse> findAll() {
        List<Earning> earnings = earningRepository.findAll();

        return  mapper.toResponseList(earnings);
    }

    public EarningResponse findById(Long id) {
        Earning earning = earningRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Earning not found with id: " + id));

        return mapper.toResponse(earning);
    }

    public EarningResponse saveEarning(EarningRequest earningRequest) {

        List<Category> categories = getCategoriesByIds(earningRequest.categoriesIds());

        Earning earning = mapper.toEntity(earningRequest);
        earning.getCategories().addAll(categories);
        earning = earningRepository.save(earning);

        return mapper.toResponse(earning);
    }

    private List<Category> getCategoriesByIds(List<Long> categoriesIds) {
        List<Category> categories = categoryRepository.findAllById(categoriesIds);

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
