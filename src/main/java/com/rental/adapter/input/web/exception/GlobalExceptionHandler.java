package com.rental.adapter.input.web.exception;

import com.rental.domain.exception.BusinessException;
import com.rental.domain.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ApiResponse(
            responseCode = "404",
            description = "엔티티를 찾을 수 없음",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
            {
                "code": "CAR_NOT_FOUND",
                "message": "요청하신 ID의 차량을 찾을 수 없습니다."
            }
            """)))
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    @ApiResponse(
            responseCode = "400",
            description = "비즈니스 규칙 위반",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
            {
                "code": "INVALID_CAR_STATUS",
                "message": "잘못된 차량 상태입니다."
            }
            """)))
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getErrorCode().name(), e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 값",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
            {
                "code": "INVALID_INPUT",
                "message": "제조사는 필수 입력값입니다."
            }
            """)))
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", message));
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(
            responseCode = "500",
            description = "서버 오류",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
               {
                   "code": "INTERNAL_SERVER_ERROR",
                   "message": "서버 오류가 발생했습니다."
               }
               """)
            )
    )
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."));
    }

    @Getter
    @Schema(description = "에러 응답")
    public static class ErrorResponse {
        @Schema(description = "에러 코드", example = "CAR_NOT_FOUND")
        private final String code;

        @Schema(description = "에러 메시지", example = "요청하신 ID의 차량을 찾을 수 없습니다.")
        private final String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}