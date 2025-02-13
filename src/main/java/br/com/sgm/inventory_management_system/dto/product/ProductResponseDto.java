package br.com.sgm.inventory_management_system.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponseDto extends ProductDto {

    @JsonProperty("categorias")
    private List<String> categories;
}