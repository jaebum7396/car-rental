package com.rental.adapter.input.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarResponse;
import com.rental.adapter.input.web.dto.CarUpdateRequest;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.application.port.input.CarService;
import com.rental.domain.car.Car;
import com.rental.domain.car.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @Test
    @DisplayName("차량 등록 API 테스트")
    void registerCar() throws Exception {
        // given
        CarCreateRequest request = new CarCreateRequest();
        request.setManufacturer("현대");
        request.setModel("코나");
        request.setProductionYear(2024);
        request.setCategoryIds(List.of(1L));

        Category category = Category.builder().name("SUV").build();
        Car car = Car.builder()
                .manufacturer("현대")
                .model("코나")
                .productionYear(2024)
                .categories(Set.of(category))
                .build();

        given(carService.registerCar(any(CarCreateRequest.class)))
                .willReturn(new CarResponse(car));

        // when & then
        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.manufacturer").value("현대"))
                .andExpect(jsonPath("$.model").value("코나"))
                .andExpect(jsonPath("$.productionYear").value(2024))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories[0].name").value("SUV"));
    }

    @Test
    @DisplayName("차량 수정 API 테스트")
    void updateCar() throws Exception {
        // given
        Long carId = 1L;
        CarUpdateRequest request = new CarUpdateRequest();
        request.setManufacturer("현대");
        request.setModel("투싼");
        request.setProductionYear(2024);
        request.setCategoryIds(List.of(2L));

        Category category = Category.builder().name("준중형 SUV").build();
        Car car = Car.builder()
                .manufacturer("현대")
                .model("투싼")
                .productionYear(2024)
                .categories(Set.of(category))
                .build();

        given(carService.updateCar(anyLong(), any(CarUpdateRequest.class)))
                .willReturn(new CarResponse(car));

        // when & then
        mockMvc.perform(put("/api/v1/cars/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manufacturer").value("현대"))
                .andExpect(jsonPath("$.model").value("투싼"))
                .andExpect(jsonPath("$.productionYear").value(2024))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories[0].name").value("준중형 SUV"))
                .andDo(print());
    }

    @Test
    @DisplayName("차량 검색 API 테스트")
    void searchCars() throws Exception {
        // given
        CarSearchCondition condition = new CarSearchCondition();
        condition.setManufacturer("현대");

        List<CarResponse> responses = List.of(new CarResponse());
        given(carService.findBySearchCondition(any(CarSearchCondition.class)))
                .willReturn(responses);

        // when & then
        mockMvc.perform(get("/api/v1/cars")
                        .param("manufacturer", "현대"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    @DisplayName("차량 가용성 변경 API 테스트")
    void changeAvailability() throws Exception {
        // given
        Long carId = 1L;
        willDoNothing().given(carService).changeAvailability(anyLong(), anyBoolean());

        // when & then
        mockMvc.perform(patch("/api/v1/cars/{id}/availability", carId)
                        .param("available", "false"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}