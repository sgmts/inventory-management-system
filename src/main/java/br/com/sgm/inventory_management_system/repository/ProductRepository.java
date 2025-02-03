package br.com.sgm.inventory_management_system.repository;

import br.com.sgm.inventory_management_system.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}