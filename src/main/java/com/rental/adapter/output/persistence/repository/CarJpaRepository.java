package com.rental.adapter.output.persistence.repository;

import com.rental.domain.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
    @Query("select distinct c from Car c left join fetch c.categories where c.id = :id")
    Optional<Car> findByIdWithCategories(@Param("id") Long id);
}
