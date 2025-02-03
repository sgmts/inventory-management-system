package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.model.product.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDto productRequestDto);

//    ProductRequestDto toDto(Product product);


}