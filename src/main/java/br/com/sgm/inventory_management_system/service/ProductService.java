package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void registerProduct(@Valid ProductRequestDto productRequestDto);

    List<ProductRequestDto> getAllProducts();

    Optional<ProductRequestDto> getProductById(Long id);

    void deleteProductById(Long id);

    void updateProduct(Long id, @Valid ProductRequestDto productDTO);
}