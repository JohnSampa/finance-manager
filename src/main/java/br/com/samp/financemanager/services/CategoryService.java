package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.CategoryMapper;
import br.com.samp.financemanager.dto.request.CategoryRequest;
import br.com.samp.financemanager.dto.request.CategoryUpdateRequest;
import br.com.samp.financemanager.dto.response.CategoryResponse;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.model.Category;
import br.com.samp.financemanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));
        return categoryMapper.toResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    public void deleteCategoryById(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));

        categoryRepository.delete(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id " + id));

        category.setDescription(categoryRequest.description());
        category.setName(categoryRequest.name());

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);

    }


}
