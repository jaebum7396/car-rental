package com.rental.application.port.output;

import com.rental.domain.car.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    void delete(Category category);
    void deleteAll();
    boolean existsById(Long id);
}
