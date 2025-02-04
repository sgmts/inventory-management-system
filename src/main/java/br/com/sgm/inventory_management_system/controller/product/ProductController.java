package br.com.sgm.inventory_management_system.controller.product;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ProductRequestDto productRequestDto) {

        productService.registerProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<ProductRequestDto> getAllProducts(
            @RequestHeader(name = "Authorization", required = true) String token) {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductRequestDto>> getProductById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de buscar produto com id {} no sistema.", id);

        var usuarioSolicitado = productService.getProductById(id);
        return new ResponseEntity<>(usuarioSolicitado, HttpStatus.OK);
    }
}