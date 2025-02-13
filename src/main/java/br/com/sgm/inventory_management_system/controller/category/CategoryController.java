package br.com.sgm.inventory_management_system.controller.category;

import br.com.sgm.inventory_management_system.dto.category.CategoryRequestResponseDto;
import br.com.sgm.inventory_management_system.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCategory(@Valid @RequestBody CategoryRequestResponseDto categoryRequestResponseDto) {
        categoryService.registerCategory(categoryRequestResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<CategoryRequestResponseDto> getAllCategorys(
            @RequestHeader(name = "Authorization", required = true) String token) {
        return categoryService.getAllCategorys();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de apagar categoria com id {} no sistema.", id);

        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}