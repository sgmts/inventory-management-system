package br.com.sgm.inventory_management_system.dto.product;

import br.com.sgm.inventory_management_system.model.product.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductRequestResponseDto {

    private String id;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @JsonProperty("nome")
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres.")
    @JsonProperty("descricao")
    private String description;

    @NotBlank(message = "O código de barras é obrigatório.")
    @Pattern(regexp = "\\d{13}", message = "O código de barras deve conter exatamente 13 dígitos numéricos.")
    @JsonProperty("codigo-barra")
    private String barCode;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    @JsonProperty("quantidade")
    private Integer stockQuantity;

    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @JsonProperty("preco")
    private BigDecimal price;

    @JsonProperty("categoria")
    private CategoryEnum categoryEnum;

    @NotBlank(message = "O nome do fornecedor é obrigatório.")
    @JsonProperty("fornecedor")
    private String supplier; // Fornecedor

    @NotNull(message = "A data de validade é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Future(message = "A data de validade deve ser uma data futura.")
    @JsonProperty("data-validade")
    private LocalDate expirationDate; // Data de Validade

    @JsonProperty("data-registro")
    private LocalDateTime registrationDate; // Data de Registro no Sistema

    @JsonProperty("ativo")
    private Boolean enabled;
}
