package com.rental.adapter.output.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.domain.car.Car;
import com.rental.domain.car.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarQueryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarJpaRepository carJpaRepository;

    private CarQueryRepository carQueryRepository;

    @BeforeEach
    void setUp() {
        carQueryRepository = new CarQueryRepository(new JPAQueryFactory(entityManager.getEntityManager()));
    }

    @Test
    @DisplayName("검색 조건으로 차량 조회")
    void findBySearchCondition() {
        // given
        Category category = Category.builder()
                .name("SUV")
                .build();
        entityManager.persist(category);

        Car car = Car.builder()
                .manufacturer("현대")
                .model("코나")
                .productionYear(2024)
                .categories(Set.of(category))
                .build();
        entityManager.persist(car);
        entityManager.flush();

        CarSearchCondition condition = new CarSearchCondition();
        condition.setManufacturer("현대");

        // when
        List<Car> results = carQueryRepository.findBySearchCondition(condition);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getManufacturer()).isEqualTo("현대");
    }
}