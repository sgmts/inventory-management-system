package br.com.sgm.inventory_management_system.dto.product;

import br.com.sgm.inventory_management_system.dto.category.CategoryRequestResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductUpdateRequestDto {

    private String id;

    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
    @JsonProperty("nome")
    private String name;

    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres.")
    @JsonProperty("descricao")
    private String description;

    @Pattern(regexp = "\\d{13}", message = "O código de barras deve conter exatamente 13 dígitos numéricos.")
    @JsonProperty("codigo-barra")
    private String barCode;

    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
    @JsonProperty("quantidade")
    private Integer stockQuantity;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @JsonProperty("preco")
    private BigDecimal price;

    @JsonProperty("fornecedor")
    private String supplier; // Fornecedor

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Future(message = "A data de validade deve ser uma data futura.")
    @JsonProperty("data-validade")
    private LocalDate expirationDate; // Data de Validade

    @JsonProperty("data-registro")
    private LocalDateTime registrationDate; // Data de Registro no Sistema

    @JsonProperty("ativo")
    private Boolean enabled;

    @JsonProperty("categoria")
    private CategoryRequestResponseDto category;

}
