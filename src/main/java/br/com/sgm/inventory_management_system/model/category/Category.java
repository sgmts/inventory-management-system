package br.com.sgm.inventory_management_system.model.category;

import br.com.sgm.inventory_management_system.model.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome da categoria deve ter entre 3 e 100 caracteres.")
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 500, message = "A descricao da categoria pode ter no máximo 500 caracteres.")
    @Column()
    private String description;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
}
