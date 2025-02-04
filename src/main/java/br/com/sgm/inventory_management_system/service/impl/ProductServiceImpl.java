package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.ProductRequestResponseDto;
import br.com.sgm.inventory_management_system.exceptions.ErrorDeletingProductException;
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
    public void registerProduct(ProductRequestResponseDto productRequestResponseDto) {
        productRequestResponseDto.setRegistrationDate(LocalDateTime.now());

        Product product = productMapper.toEntity(productRequestResponseDto);

        productRepository.save(product);
    }

    public List<ProductRequestResponseDto> getAllProducts() {
        log.info("Iniciando a busca de produtos no sistema.");

        List<Product> productsList = productRepository.findAll();
        log.info("Número total de produtos encontrados: {}", productsList.size());

        // Usando o UsuarioMapper para converter a lista
        List<ProductRequestResponseDto> productsDTOList = productsList.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        log.info("Conversão para DTOs concluída. Total de produtos processados: {}", productsDTOList.size());

        return productsDTOList;
    }

    @Override
    public Optional<ProductRequestResponseDto> getProductById(Long id) {
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

    public void deleteProductById(Long id) {
        log.info("Iniciando a exclusão do produto com ID {} no sistema.", id);

        if (!productRepository.existsById(id)) {
            log.warn("Tentativa de excluir produto com ID {} que não existe.", id);
            throw new ProductNotFoundException();
        }
        try {
            productRepository.deleteById(id);
            log.info("Produto com ID {} excluído com sucesso.", id);
        } catch (Exception ex) {
            log.error("Erro ao excluir o produto com ID {}. Detalhes: {}", id, ex.getMessage());
            throw new ErrorDeletingProductException();
        }
    }

    @Override
    public void updateProduct(Long id, ProductRequestResponseDto newProduct) {
        log.info("Iniciando a atualização do produto com id {} no sistema.", id);

        // Busca o produto existente no banco
        Product productUpdated = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto com ID {} nao encontrado no sistema", id);
                    return new ProductNotFoundException();
                });

        // Atualiza os campos do usuário
        log.info("Atualizando os dados do produto com ID {}.", id);
        productUpdated.setName(newProduct.getName());
        productUpdated.setDescription(newProduct.getDescription());
        productUpdated.setBarCode(newProduct.getBarCode());
        productUpdated.setStockQuantity(newProduct.getStockQuantity());
        productUpdated.setPrice(newProduct.getPrice());
        productUpdated.setCategory(newProduct.getCategory());
        productUpdated.setSupplier(newProduct.getSupplier());
        productUpdated.setExpirationDate(newProduct.getExpirationDate());

        // Salva o usuário atualizado
        productRepository.save(productUpdated);
        log.info("Produto com ID {} atualizado com sucesso.", id);
    }
}