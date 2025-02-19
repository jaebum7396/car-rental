package com.rental.adapter.input.web.dto;

import com.rental.domain.car.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static CategoryResponse of(Category category) {
        return new CategoryResponse(category);
    }
}