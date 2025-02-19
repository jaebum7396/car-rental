package com.rental.application.service;

import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarResponse;
import com.rental.adapter.input.web.dto.CarUpdateRequest;
import com.rental.adapter.output.persistence.CarRepositoryAdapter;
import com.rental.adapter.output.persistence.CategoryRepositoryAdapter;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.application.port.input.CarService;
import com.rental.domain.car.Car;
import com.rental.domain.car.Category;
import com.rental.domain.exception.EntityNotFoundException;
import com.rental.domain.exception.ErrorCode;
import com.rental.domain.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepositoryAdapter carRepository;
    private final CategoryRepositoryAdapter categoryRepository;

    @Override
    public CarResponse registerCar(CarCreateRequest request) {
        Set<Category> categories = getCategoriesFromIds(request.getCategoryIds());
        validateCategories(categories);

        Car car = Car.builder()
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .productionYear(request.getProductionYear())
                .categories(categories)
                .build();

        return CarResponse.of(carRepository.save(car));
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponse findById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAR_NOT_FOUND));
        return CarResponse.of(car);
    }

    private Set<Category> getCategoriesFromIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponse> findBySearchCondition(CarSearchCondition condition) {
        return carRepository.findBySearchCondition(condition).stream().map(CarResponse::of)
                .collect(Collectors.toList());
    }

    private void validateCategories(Set<Category> categories) {
        if (categories.isEmpty()) {
            throw new InvalidValueException(ErrorCode.CATEGORY_REQUIRED);
        }
    }

    @Override
    public CarResponse updateCar(Long id, CarUpdateRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAR_NOT_FOUND));

        // 기본 정보 업데이트
        car.update(request.getManufacturer(), request.getModel(), request.getProductionYear());

        // 카테고리 업데이트 (요청에 카테고리가 포함된 경우)
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> newCategories = getCategoriesFromIds(request.getCategoryIds());
            car.getCategories().clear();
            newCategories.forEach(car::addCategory);
        }
        return CarResponse.of(car);
    }

    @Override
    public void changeAvailability(Long id, boolean available) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAR_NOT_FOUND));
        car.changeAvailability(available);
    }
}