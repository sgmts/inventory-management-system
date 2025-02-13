package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.category.CategoryRequestResponseDto;
import br.com.sgm.inventory_management_system.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestResponseDto categoryRequestResponseDto);

    CategoryRequestResponseDto toDto(Category category);

}