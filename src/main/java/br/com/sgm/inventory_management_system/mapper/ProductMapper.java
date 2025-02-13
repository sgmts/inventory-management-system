package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.product.ProductRequestDto;
import br.com.sgm.inventory_management_system.dto.product.ProductResponseDto;
import br.com.sgm.inventory_management_system.model.category.Category;
import br.com.sgm.inventory_management_system.model.product.Product;
import br.com.sgm.inventory_management_system.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoryIdsToCategories")
    public abstract Product toEntity(ProductRequestDto productRequestDto);

    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoriesToCategoryNames")
    public abstract ProductResponseDto toDto(Product product);

    @Named("mapCategoryIdsToCategories")
    List<Category> mapCategoryIdsToCategories(List<Long> categoryIds) {
        return categoryIds != null ? categoryRepository.findAllById(categoryIds) : List.of();
    }

    @Named("mapCategoriesToCategoryNames")
    List<String> mapCategoriesToCategoryNames(List<Category> categories) {
        return categories != null ? categories.stream().map(Category::getName).collect(Collectors.toList()) : List.of();
    }

    public Optional<ProductResponseDto> toOptionalDto(Optional<Product> optionalProduct) {
        return optionalProduct.map(this::toDto);
    }

}
