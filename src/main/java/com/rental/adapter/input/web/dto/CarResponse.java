package com.rental.adapter.input.web.dto;

import com.rental.domain.car.Car;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "자동차 응답 DTO")
@Getter
@NoArgsConstructor
public class CarResponse {

    @Schema(description = "자동차 ID", example = "1")
    private Long id;

    @Schema(description = "제조사", example = "현대")
    private String manufacturer;

    @Schema(description = "모델명", example = "코나")
    private String model;

    @Schema(description = "생산년도", example = "2024")
    private Integer productionYear;

    @Schema(description = "대여 가능 여부", example = "true")
    private boolean available;

    @Schema(description = "카테고리 목록")
    private List<CategoryResponse> categories;

    @Schema(description = "생성일시", example = "2024-02-19T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시", example = "2024-02-19T10:00:00")
    private LocalDateTime updatedAt;

    public static CarResponse of(Car car) {
        return new CarResponse(car);
    }

    public CarResponse(Car car) {
        this.id = car.getId();
        this.manufacturer = car.getManufacturer();
        this.model = car.getModel();
        this.productionYear = car.getProductionYear();
        this.available = car.isAvailable();
        this.categories = car.getCategories().stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
        this.createdAt = car.getCreatedAt();
        this.updatedAt = car.getUpdatedAt();
    }
}