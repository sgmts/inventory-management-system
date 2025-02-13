package br.com.sgm.inventory_management_system.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto extends ProductDto {

    @JsonProperty("categorias")
    private List<Long> categories; // Apenas os IDs das categorias

}
