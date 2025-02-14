package br.com.sgm.inventory_management_system.repository;

import br.com.sgm.inventory_management_system.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}