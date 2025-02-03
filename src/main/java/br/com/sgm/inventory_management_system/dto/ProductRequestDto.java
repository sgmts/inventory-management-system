package br.com.sgm.inventory_management_system.dto;

import br.com.sgm.inventory_management_system.model.product.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductRequestDto {

    @JsonProperty("nome")
    private String name;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("codigo-barra")
    private String barCode;

    @JsonProperty("quantidade")
    private String stockQuantity;

    @JsonProperty("preco")
    private BigDecimal price;

    @JsonProperty("categoria")
    private Category category;

    @JsonProperty("fornecedor")
    private String supplier; // Fornecedor

    @NotNull(message = "A data de validade é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("data-validade")
    private LocalDate expirationDate; // Data de Validade

    private LocalDateTime registrationDate ; // Data de Registro no Sistema

    @JsonProperty("isActive")
    private Boolean isActive = true;
}
