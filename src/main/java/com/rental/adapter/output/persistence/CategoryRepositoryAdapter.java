package com.rental.adapter.output.persistence;

import com.rental.adapter.output.persistence.repository.CategoryJpaRepository;
import com.rental.application.port.output.CategoryRepository;
import com.rental.domain.car.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public void delete(Category category) {
        categoryJpaRepository.delete(category);
    }

    @Override
    public void deleteAll() {
        categoryJpaRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return categoryJpaRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return categoryJpaRepository.existsByName(name);
    }
}