package com.rental.application.port.output;

import com.rental.domain.car.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    Optional<Car> findById(Long id);
    List<Car> findAll();
    void delete(Car car);
    void deleteAll();
    boolean existsById(Long id);
}
