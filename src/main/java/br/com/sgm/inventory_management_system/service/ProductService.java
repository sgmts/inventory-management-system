package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import jakarta.validation.Valid;

public interface ProductService {

    void registerProduct(@Valid ProductRequestDto productRequestDto);
}