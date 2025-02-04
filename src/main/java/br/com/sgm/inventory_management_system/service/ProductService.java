package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    void registerProduct(@Valid ProductRequestDto productRequestDto);

    List<ProductRequestDto> getAllProducts();
}