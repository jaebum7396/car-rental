package com.rental.application.port.input;

import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarResponse;
import com.rental.adapter.input.web.dto.CarUpdateRequest;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;

import java.util.List;

public interface CarService {
    CarResponse registerCar(CarCreateRequest request);
    CarResponse updateCar(Long id, CarUpdateRequest request);
    CarResponse findById(Long id);
    List<CarResponse> findBySearchCondition(CarSearchCondition condition);
    void changeAvailability(Long id, boolean available);
}