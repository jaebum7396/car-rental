package com.rental.domain.car;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Car 도메인 테스트")
class CarTest {
    @Test
    @DisplayName("자동차 생성 테스트")
     void createCar() {
        // given
        String manufacturer = "현대";
        String model = "코나";
        int productionYear = 2024;
        Set<Category> categories = Set.of(new Category("SUV"));

        // when
        Car car = Car.builder()
                .manufacturer(manufacturer)
                .model(model)
                .productionYear(productionYear)
                .categories(categories)
                .build();

        // then
        assertThat(car.getManufacturer()).isEqualTo(manufacturer);
        assertThat(car.getModel()).isEqualTo(model);
        assertThat(car.getProductionYear()).isEqualTo(productionYear);
        assertThat(car.getCategories()).hasSize(1);
        assertThat(car.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("자동차 정보 수정 테스트")
    void updateCar() {
        // given
        Car car = Car.builder()
                .manufacturer("현대")
                .model("코나")
                .productionYear(2024)
                .build();

        // when
        car.update("기아", "쏘렌토", 2025);

        // then
        assertThat(car.getManufacturer()).isEqualTo("기아");
        assertThat(car.getModel()).isEqualTo("쏘렌토");
        assertThat(car.getProductionYear()).isEqualTo(2025);
    }

    @Test
    @DisplayName("자동차 가용성 변경 테스트")
    void changeAvailability() {
        // given
        Car car = Car.builder()
                .manufacturer("현대")
                .model("코나")
                .productionYear(2024)
                .build();

        // when
        car.changeAvailability(false);

        // then
        assertThat(car.isAvailable()).isFalse();
    }
}