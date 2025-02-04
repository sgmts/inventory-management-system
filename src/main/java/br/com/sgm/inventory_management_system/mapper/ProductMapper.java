package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.model.product.Product;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto productRequestDto);

    ProductRequestDto toDto(Product product);

    default Optional<ProductRequestDto> toOptionalDto(Optional<Product> optionalProduct) {
        return optionalProduct.map(this::toDto);
    }

    default Optional<Product> toOptionalEntity(Optional<ProductRequestDto> optionalProductDto) {
        return optionalProductDto.map(this::toEntity);
    }
}