package br.com.sgm.inventory_management_system.controller.product;

import br.com.sgm.inventory_management_system.dto.product.ProductRequestResponseDto;
import br.com.sgm.inventory_management_system.dto.product.ProductUpdateRequestDto;
import br.com.sgm.inventory_management_system.service.ProductService;
import br.com.sgm.inventory_management_system.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ProductRequestResponseDto productRequestResponseDto) {

        productService.registerProduct(productRequestResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<ProductRequestResponseDto> getAllProducts(
            @RequestHeader(name = "Authorization", required = true) String bearerToken) {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductRequestResponseDto>> getProductById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String bearerToken) {
        log.info("Tentativa de buscar produto com id {} no sistema.", id);

        var usuarioSolicitado = productService.getProductById(id);
        return new ResponseEntity<>(usuarioSolicitado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String bearerToken) {
        log.info("Tentativa de apagar produto com id {} no sistema.", id);

        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductUpdateRequestDto> updateProductById(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequestDto productDTO,
            @RequestHeader(name = "Authorization", required = true) String bearerToken) {
        log.info("Tentativa de atualizar produto com id {} no sistema.", id);

        String userEmail = jwtUtil.extractEmailUser(bearerToken);
        productService.updateProduct(id, productDTO, userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}