package br.com.sgm.inventory_management_system.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column()
    private String barCode;

    @Column()
    private String stockQuantity;

    @Column()
    private BigDecimal price;

    @Column()
    private Category category;

    @Column()
    private String supplier; // Fornecedor

    @NotNull(message = "XXX")
    @Column()
    private LocalDate expirationDate; // Data de Validade

    @Column()
    private LocalDateTime registrationDate; // Data de registro

    @Column()
    private Boolean enabled;

}
