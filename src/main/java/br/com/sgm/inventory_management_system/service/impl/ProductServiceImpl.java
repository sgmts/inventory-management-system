package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.product.ProductRequestResponseDto;
import br.com.sgm.inventory_management_system.dto.product.ProductUpdateRequestDto;
import br.com.sgm.inventory_management_system.exceptions.CategoryNotFoundException;
import br.com.sgm.inventory_management_system.exceptions.ErrorDeletingProductException;
import br.com.sgm.inventory_management_system.exceptions.ProductNotFoundException;
import br.com.sgm.inventory_management_system.mapper.ProductMapper;
import br.com.sgm.inventory_management_system.model.category.Category;
import br.com.sgm.inventory_management_system.model.product.Product;
import br.com.sgm.inventory_management_system.repository.CategoryRepository;
import br.com.sgm.inventory_management_system.repository.ProductRepository;
import br.com.sgm.inventory_management_system.service.AuditService;
import br.com.sgm.inventory_management_system.service.ProductService;
import br.com.sgm.inventory_management_system.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.sgm.inventory_management_system.model.audit.AuditEnum.UPDATE_PRODUCT;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    private final AuditService auditService;

    @Override
    public void registerProduct(ProductRequestResponseDto productRequestResponseDto) {

        Product product = productMapper.toEntity(productRequestResponseDto);

        if (productRequestResponseDto.getCategory() != null) {
            Category category = categoryRepository.findByName(productRequestResponseDto.getCategory().getName());

            if (null == category) {
                throw new CategoryNotFoundException();
            }

            product.setCategory(category); // GARANTE QUE AS CATEGORIAS SEJAM DEFINIDAS
        } else {
            throw new RuntimeException("O PRODUTO DEVE TER AO MENOS UMA CATEGORIA");
        }


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

    public void updateProduct(Long id, ProductUpdateRequestDto newProduct, String userEmail) {
        log.info("Iniciando a atualização do produto com id {} no sistema.", id);

        // Busca o produto existente no banco
        Product productUpdated = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Produto com ID {} nao encontrado no sistema", id);
                    return new ProductNotFoundException();
                });

        Product newProductEntity = productMapper.toEntityUpdate(newProduct);
        // Audita automaticamente qualquer campo que foi alterado
        auditService.logEntityChanges(UPDATE_PRODUCT, newProductEntity.getClass().getSimpleName(), productUpdated, newProductEntity, userEmail);

        log.info("Atualizando os dados do produto com ID {}.", id);

        if (newProduct.getName() != null) productUpdated.setName(newProduct.getName());
        if (newProduct.getDescription() != null) productUpdated.setDescription(newProduct.getDescription());
        if (newProduct.getBarCode() != null) newProduct.setBarCode(newProduct.getBarCode());
        if (newProduct.getStockQuantity() != null) productUpdated.setStockQuantity(newProduct.getStockQuantity());
        if (newProduct.getPrice() != null) productUpdated.setPrice(newProduct.getPrice());
        if (newProduct.getSupplier() != null) productUpdated.setSupplier(newProduct.getSupplier());
        if (newProduct.getExpirationDate() != null) productUpdated.setExpirationDate(newProduct.getExpirationDate());
        if (newProduct.getEnabled() != null) productUpdated.setEnabled(newProduct.getEnabled());

        if (!(newProduct.getCategory() == null)) {
            Category category = categoryRepository.findByName(newProduct.getCategory().getName());
            productUpdated.setCategory(category);
        }

        // Salva o usuário atualizado
        productRepository.save(productUpdated);
        log.info("Produto com ID {} atualizado com sucesso.", id);
    }
}