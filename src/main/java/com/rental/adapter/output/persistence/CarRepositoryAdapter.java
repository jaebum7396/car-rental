package com.rental.adapter.output.persistence;

import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.adapter.output.persistence.repository.CarJpaRepository;
import com.rental.adapter.output.persistence.repository.CarQueryRepository;
import com.rental.application.port.output.CarRepository;
import com.rental.domain.car.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarRepository {
    private final CarJpaRepository carJpaRepository;
    private final CarQueryRepository carQueryRepository;

    @Override
    public Car save(Car car) {
        return carJpaRepository.save(car);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carJpaRepository.findByIdWithCategories(id);
    }

    @Override
    public List<Car> findAll() {
        return carJpaRepository.findAll();
    }

    @Override
    public void delete(Car car) {
        carJpaRepository.delete(car);
    }

    @Override
    public void deleteAll() {
        carJpaRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return carJpaRepository.existsById(id);
    }

    public List<Car> findBySearchCondition(CarSearchCondition condition) {
        return carQueryRepository.findBySearchCondition(condition);
    }

    public List<Car> findByCategories(List<Long> categoryIds) {
        return carQueryRepository.findByCategories(categoryIds);
    }

    public Optional<Car> findDetailById(Long id) {
        return carQueryRepository.findDetailById(id);
    }
}