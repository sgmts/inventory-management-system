package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.category.CategoryRequestResponseDto;

import java.util.List;

public interface CategoryService {

    void registerCategory (CategoryRequestResponseDto categoryRequestResponseDto);

    List<CategoryRequestResponseDto> getAllCategorys();

    void deleteCategoryById(Long id);
}