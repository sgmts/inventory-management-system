package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.product.ProductRequestResponseDto;
import br.com.sgm.inventory_management_system.dto.product.ProductUpdateRequestDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void registerProduct(ProductRequestResponseDto productRequestResponseDto);

    List<ProductRequestResponseDto> getAllProducts();

    Optional<ProductRequestResponseDto> getProductById(Long id);

    void deleteProductById(Long id);

    void updateProduct(Long id, ProductUpdateRequestDto productRequestResponseDto, String userEmail);
}