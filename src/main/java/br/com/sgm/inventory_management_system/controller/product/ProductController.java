package br.com.sgm.inventory_management_system.controller.product;

import br.com.sgm.inventory_management_system.dto.ProductRequestDto;
import br.com.sgm.inventory_management_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}