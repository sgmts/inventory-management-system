package br.com.sgm.inventory_management_system.model.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O CEP é obrigatório.")
    @Size(max = 8)
    @Column(nullable = false)
    private String cep;

    @Column()
    private String street;

    @Column(nullable = false)
    private String number;

    @Column()
    private String addressComplement;

    @Column()
    private String neighborhood;

    @Column()
    private String city;

    @Column()
    private String uf;

    @Column()
    private String region;
}