package com.rental.adapter.output.persistence.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.domain.car.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rental.domain.car.QCar.car;
import static com.rental.domain.car.QCategory.category;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CarQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Car> findBySearchCondition(CarSearchCondition condition) {
        return queryFactory
        .selectFrom(car)
        .distinct()
        .leftJoin(car.categories, category)
        .fetchJoin()
        .where(
            manufacturerEq(condition.getManufacturer()), // 제조사
            modelEq(condition.getModel()), // 모델
            productionYearEq(condition.getProductionYear()), // 생산년도
            categoriesIn(condition.getCategoryIds()), // 카테고리
            availableEq(condition.getAvailable()) // 대여 가능 여부
        )
        .fetch();
    }

    private BooleanExpression manufacturerEq(String manufacturer) {
        return hasText(manufacturer) ?
                car.manufacturer.eq(manufacturer) : null;
    }

    private BooleanExpression modelEq(String model) {
        return hasText(model) ?
                car.model.eq(model) : null;
    }

    private BooleanExpression productionYearEq(Integer productionYear) {
        return productionYear != null ?
                car.productionYear.eq(productionYear) : null;
    }

    private BooleanExpression categoriesIn(List<Long> categoryIds) {
        return categoryIds != null && !categoryIds.isEmpty() ?
                car.categories.any().id.in(categoryIds) : null;
    }

    private BooleanExpression availableEq(Boolean available) {
        return available != null ?
                car.available.eq(available) : null;
    }

    public List<Car> findByCategories(List<Long> categoryIds) {
        return queryFactory
        .selectFrom(car)
        .distinct()
        .leftJoin(car.categories, category)
        .fetchJoin()
        .where(car.categories.any().id.in(categoryIds))
        .fetch();
    }

    public Optional<Car> findDetailById(Long id) {
        Car result = queryFactory
        .selectFrom(car)
        .leftJoin(car.categories, category)
        .fetchJoin()
        .where(car.id.eq(id))
        .fetchOne();

        return Optional.ofNullable(result);
    }
}