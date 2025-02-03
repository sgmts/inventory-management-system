package br.com.sgm.inventory_management_system.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false)
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres.")
    @Column()
    private String description;

    @NotBlank(message = "O código de barras é obrigatório.")
    @Pattern(regexp = "\\d{13}", message = "O código de barras deve conter exatamente 13 dígitos numéricos.")
    @Column(unique = true, nullable = false)
    private String barCode;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    @Column(nullable = false)
    private Integer stockQuantity;

    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "A categoria do produto é obrigatória.")
    private Category category;

    @NotBlank(message = "O nome do fornecedor é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do fornecedor deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false)
    private String supplier; // Fornecedor

    @NotNull(message = "A data de validade é obrigatória.")
    @Future(message = "A data de validade deve ser uma data futura.")
    @Column(nullable = false)
    private LocalDate expirationDate; // Data de Validade

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate; // Data de registro

    @Column()
    private Boolean enabled;

}
