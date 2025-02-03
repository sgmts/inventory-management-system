package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.mapper.ProductMapper;
import br.com.sgm.inventory_management_system.model.product.Product;
import br.com.sgm.inventory_management_system.repository.ProductRepository;
import br.com.sgm.inventory_management_system.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    @Override
    public void registerProduct(ProductRequestDto productRequestDto) {
        productRequestDto.setRegistrationDate(LocalDateTime.now());

        Product product = productMapper.toEntity(productRequestDto);

        productRepository.save(product);
    }
}