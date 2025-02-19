package com.rental.adapter.input.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "자동차 수정 요청 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CarUpdateRequest {
    @Schema(
            description = "제조사",
            example = "현대",
            required = true
    )
    @NotBlank
    private String manufacturer;

    @Schema(
            description = "모델명",
            example = "코나",
            required = true
    )
    @NotBlank
    private String model;

    @Schema(
            description = "생산년도",
            example = "2024",
            minimum = "1900",
            required = true
    )
    @NotNull
    @Min(1900)
    private Integer productionYear;

    @Schema(
            description = "카테고리 ID 목록",
            example = "[1, 2]",
            required = true
    )
    private List<Long> categoryIds; // 카테고리 ID
}