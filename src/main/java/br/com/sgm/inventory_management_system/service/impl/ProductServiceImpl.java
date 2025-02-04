package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.exceptions.ProductNotFoundException;
import br.com.sgm.inventory_management_system.mapper.ProductMapper;
import br.com.sgm.inventory_management_system.model.product.Product;
import br.com.sgm.inventory_management_system.repository.ProductRepository;
import br.com.sgm.inventory_management_system.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ProductRequestDto> getAllProducts() {
        log.info("Iniciando a busca de produtos no sistema.");

        List<Product> productsList = productRepository.findAll();
        log.info("Número total de produtos encontrados: {}", productsList.size());

        // Usando o UsuarioMapper para converter a lista
        List<ProductRequestDto> productsDTOList = productsList.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        log.info("Conversão para DTOs concluída. Total de produtos processados: {}", productsDTOList.size());

        return productsDTOList;
    }

    @Override
    public Optional<ProductRequestDto> getProductById(Long id) {
        log.info("Iniciando a busca do produto {} no sistema.", id);

        Optional<Product> requestedProduct = productRepository.findById(id);

        // Verifica se o usuário foi encontrado
        if (requestedProduct.isEmpty()) {
            log.warn("Produto com ID {} não encontrado no sistema.", id);
            throw new ProductNotFoundException();
        }

        log.info("Produto com ID {} encontrado no sistema.", id);
        return productMapper.toOptionalDto(requestedProduct);
    }
}