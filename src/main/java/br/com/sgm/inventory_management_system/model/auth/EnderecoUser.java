package br.com.sgm.inventory_management_system.model.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco_user")
public class EnderecoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cep;

    @Column()
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column()
    private String complemento;

    @Column()
    private String bairro;

    @Column()
    private String cidade;

    @Column()
    private String uf;

    @Column()
    private String regiao;
}