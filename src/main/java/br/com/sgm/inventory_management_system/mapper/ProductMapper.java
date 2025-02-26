package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.product.ProductRequestResponseDto;
import br.com.sgm.inventory_management_system.dto.product.ProductUpdateRequestDto;
import br.com.sgm.inventory_management_system.model.product.Product;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toEntity(ProductRequestResponseDto productRequestResponseDto);

    Product toEntityUpdate(ProductUpdateRequestDto productRequestResponseDto);

    ProductRequestResponseDto toDto(Product product);

    default Optional<ProductRequestResponseDto> toOptionalDto(Optional<Product> optionalProduct) {
        return optionalProduct.map(this::toDto);
    }

}
