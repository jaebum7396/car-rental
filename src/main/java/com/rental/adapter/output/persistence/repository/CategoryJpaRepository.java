package com.rental.adapter.output.persistence.repository;

import com.rental.domain.car.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
