package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.CategoryMapper;
import br.com.samp.financemanager.dto.request.CategoryRequest;
import br.com.samp.financemanager.dto.request.CategoryUpdateRequest;
import br.com.samp.financemanager.dto.response.CategoryResponse;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.model.Category;
import br.com.samp.financemanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.toResponseList(categories);
    }

    public CategoryResponse getCategoryById(UUID id) {
        Category category = categoryRepository.findByUuid(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void deleteCategoryByUuid(UUID id) {
        Category category = categoryRepository
                .findByUuid(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));

        try {
            categoryRepository.delete(category);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException(
                    "The category cannot be deleted, as this would cause inconsistencies in the system.");
        }
    }

    public CategoryResponse updateCategory(UUID id, CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findByUuid(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));

        category.setDescription(categoryRequest.description());
        category.setName(categoryRequest.name());

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);

    }


}
