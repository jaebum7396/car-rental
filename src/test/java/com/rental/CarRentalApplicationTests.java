package com.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarUpdateRequest;
import com.rental.application.port.output.CarRepository;
import com.rental.application.port.output.CategoryRepository;
import com.rental.domain.car.Car;
import com.rental.domain.car.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarRentalApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category setupCategory;
    private Car setupCar;

    @BeforeEach
    void setUp() {
        // 테스트 전 기존 데이터 삭제
        carRepository.deleteAll();
        categoryRepository.deleteAll();

        // 테스트용 카테고리 생성
        setupCategory = categoryRepository.save(
                Category.builder()
                        .name("테스트 카테고리")
                        .build()
        );

        // 테스트용 차량 생성
        setupCar = carRepository.save(
                Car.builder()
                        .manufacturer("현대")
                        .model("코나")
                        .productionYear(2024)
                        .categories(Set.of(setupCategory))
                        .build()
        );
    }

    @Nested
    @DisplayName("차량 API 통합 테스트")
    class CarApiTest {

        @Test
        @DisplayName("차량 등록 성공")
        void registerCar() throws Exception {
            // given
            CarCreateRequest request = new CarCreateRequest();
            request.setManufacturer("현대");
            request.setModel("투싼");
            request.setProductionYear(2024);
            request.setCategoryIds(List.of(setupCategory.getId()));

            // when & then
            mockMvc.perform(post("/api/v1/cars")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.manufacturer").value("현대"))
                    .andExpect(jsonPath("$.model").value("투싼"))
                    .andDo(print());
        }

        @Test
        @DisplayName("차량 수정 성공")
        void updateCar() throws Exception {
            // given
            CarUpdateRequest request = new CarUpdateRequest();
            request.setManufacturer("기아");
            request.setModel("K5");
            request.setProductionYear(2024);
            request.setCategoryIds(List.of(setupCategory.getId()));

            // when & then
            mockMvc.perform(put("/api/v1/cars/{id}", setupCar.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.manufacturer").value("기아"))
                    .andExpect(jsonPath("$.model").value("K5"))
                    .andDo(print());
        }

        @Test
        @DisplayName("차량 검색 성공")
        void searchCars() throws Exception {
            mockMvc.perform(get("/api/v1/cars")
                            .param("manufacturer", "현대"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].manufacturer").value("현대"))
                    .andExpect(jsonPath("$[0].model").value("코나"))
                    .andDo(print());
        }

        @Test
        @DisplayName("차량 가용성 변경 성공")
        void changeAvailability() throws Exception {
            mockMvc.perform(patch("/api/v1/cars/{id}/availability", setupCar.getId())
                            .param("available", "false"))
                    .andExpect(status().isOk());

            Car updatedCar = carRepository.findById(setupCar.getId()).orElseThrow();
            assertThat(updatedCar.isAvailable()).isFalse();
        }
    }

    @Nested
    @DisplayName("차량 API 예외 테스트")
    class CarApiExceptionTest {

        @Test
        @DisplayName("존재하지 않는 차량 조회시 404 응답")
        void getCarNotFound() throws Exception {
            mockMvc.perform(get("/api/v1/cars/{id}", 999999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                    .andDo(print());
        }

        @Test
        @DisplayName("잘못된 입력값으로 차량 등록시 400 응답")
        void registerCarWithInvalidInput() throws Exception {
            CarCreateRequest request = new CarCreateRequest();
            // 필수 값 누락

            mockMvc.perform(post("/api/v1/cars")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }
}
