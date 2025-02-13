package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.category.CategoryRequestResponseDto;
import br.com.sgm.inventory_management_system.exceptions.CategoryNotFoundException;
import br.com.sgm.inventory_management_system.exceptions.ErrorDeletingCategoryException;
import br.com.sgm.inventory_management_system.mapper.CategoryMapper;
import br.com.sgm.inventory_management_system.model.category.Category;
import br.com.sgm.inventory_management_system.repository.CategoryRepository;
import br.com.sgm.inventory_management_system.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public void registerCategory (CategoryRequestResponseDto categoryRequestResponseDto) {
        Category category = categoryMapper.toEntity(categoryRequestResponseDto);
        categoryRepository.save(category);
    }

    public List<CategoryRequestResponseDto> getAllCategorys() {
        List<Category> categoriesList = categoryRepository.findAll();

        return categoriesList.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public void deleteCategoryById(Long id) {
        log.info("Iniciando a exclusão da categoria com ID {} no sistema.", id);

        if (!categoryRepository.existsById(id)) {
            log.warn("Tentativa de excluir categoria com ID {} que não existe.", id);
            throw new CategoryNotFoundException();
        }
        try {
            categoryRepository.deleteById(id);
            log.info("Categoria com ID {} excluída com sucesso.", id);
        } catch (Exception ex) {
            log.error("Erro ao excluir a categoria com ID {}. Detalhes: {}", id, ex.getMessage());
            throw new ErrorDeletingCategoryException();
        }
    }
}