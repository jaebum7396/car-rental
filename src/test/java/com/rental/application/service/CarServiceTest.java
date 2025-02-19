package com.rental.application.service;

import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarResponse;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.application.port.input.CarService;
import com.rental.application.port.output.CarRepository;
import com.rental.application.port.output.CategoryRepository;
import com.rental.domain.car.Car;
import com.rental.domain.car.Category;
import com.rental.domain.exception.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CarServiceTest {
    @Autowired
    private CarService carService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    @Test
    @DisplayName("차량 등록 성공 테스트")
    void registerCarSuccess() {
        // given
        Category category = categoryRepository.save(Category.builder()
                .name("SUV")
                .build());

        CarCreateRequest request = new CarCreateRequest();
        request.setManufacturer("현대");
        request.setModel("코나");
        request.setProductionYear(2024);
        request.setCategoryIds(List.of(category.getId()));

        // when
        CarResponse response = carService.registerCar(request);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getManufacturer()).isEqualTo("현대");
        assertThat(response.getModel()).isEqualTo("코나");
        assertThat(response.getProductionYear()).isEqualTo(2024);
        assertThat(response.getCategories()).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 없이 차량 등록 시 실패")
    void registerCarWithoutCategoryFail() {
        // given
        CarCreateRequest request = new CarCreateRequest();
        request.setManufacturer("현대");
        request.setModel("코나");
        request.setProductionYear(2024);
        request.setCategoryIds(Collections.emptyList());

        // when & then
        assertThrows(InvalidValueException.class, () -> carService.registerCar(request));
    }

    @Test
    @DisplayName("조건별 차량 검색 테스트")
    void searchCarsByCondition() {
        // given
        Category category = categoryRepository.save(Category.builder()
                .name("SUV")
                .build());

        Car car = Car.builder()
                .manufacturer("현대")
                .model("코나")
                .productionYear(2024)
                .categories(Set.of(category))
                .build();
        carRepository.save(car);

        CarSearchCondition condition = new CarSearchCondition();
        condition.setManufacturer("현대");

        // when
        List<CarResponse> responses = carService.findBySearchCondition(condition);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getManufacturer()).isEqualTo("현대");
    }
}