package com.rental.adapter.input.web;

import com.rental.adapter.input.web.dto.CarCreateRequest;
import com.rental.adapter.input.web.dto.CarResponse;
import com.rental.adapter.input.web.dto.CarUpdateRequest;
import com.rental.adapter.output.persistence.condition.CarSearchCondition;
import com.rental.application.port.input.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Car", description = "자동차 API")
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @Operation(summary = "자동차 등록", description = "새로운 자동차를 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "자동차 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<CarResponse> registerCar(
        @RequestBody @Valid CarCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carService.registerCar(request));
    }

    @Operation(summary = "자동차 수정", description = "자동차 정보를 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "자동차 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(
            @Parameter(description = "자동차 ID") @PathVariable Long id,
            @RequestBody @Valid CarUpdateRequest request
    ) {
        return ResponseEntity.ok(carService.updateCar(id, request));
    }

    @Operation(summary = "자동차 조회", description = "ID로 자동차를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "자동차 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCar(
        @Parameter(description = "자동차 ID") @PathVariable Long id
    ) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @Operation(summary = "자동차 검색", description = "조건에 따라 자동차를 검색합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "검색 성공"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<List<CarResponse>> searchCars(
        @Parameter(description = "검색 조건") CarSearchCondition condition
    ) {
        return ResponseEntity.ok(carService.findBySearchCondition(condition));
    }

    @Operation(summary = "자동차 가용성 변경", description = "자동차의 대여 가능 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "404", description = "자동차 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> changeAvailability(
            @Parameter(description = "자동차 ID") @PathVariable Long id,
            @Parameter(description = "대여 가능 여부") @RequestParam boolean available
    ) {
        carService.changeAvailability(id, available);
        return ResponseEntity.ok().build();
    }
}