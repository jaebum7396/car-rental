package com.rental.adapter.output.persistence.condition;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarSearchCondition {
    private String manufacturer;
    private String model;
    private Integer productionYear;
    private List<Long> categoryIds;
    private Boolean available;
}
