package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.CategoryRequest;
import br.com.samp.financemanager.dto.CategoryResponse;
import br.com.samp.financemanager.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

}
