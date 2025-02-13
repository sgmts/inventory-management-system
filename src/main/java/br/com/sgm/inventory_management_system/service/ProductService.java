package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.product.ProductRequestDto;
import br.com.sgm.inventory_management_system.dto.product.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void registerProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();

    Optional<ProductResponseDto> getProductById(Long id);

    void deleteProductById(Long id);

    void updateProduct(Long id, ProductRequestDto productDTO);
}