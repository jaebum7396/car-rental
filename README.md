# 자동차 대여 시스템 - Car Rental Service

## 프로젝트 소개 - Introduction
헥사고날 아키텍쳐를 적용한 백엔드 애플리케이션입니다.
자동차 렌탈 도메인을 가정하여 작성하였습니다.

## 개발 환경 - Development Environment
* Java 17
* Spring Boot 3.2.3
* H2 Database
* Spring Data JPA
* Querydsl
* Spring Doc OpenAPI

## 시작하기 - Getting Started

### 빌드 및 실행 - Build & Run
```bash
./gradlew clean build
java -jar build/libs/car-rental-0.0.1-SNAPSHOT.jar
```

## API 문서 - API Documentation

### 기본 정보 - Base Information
* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* Base URL: `http://localhost:8080/api/v1`

### 자동차 등록 - Register Car
```http
POST /cars
Content-Type: application/json

{
  "manufacturer": "현대",
  "model": "코나",
  "productionYear": 2024,
  "categoryIds": [1]
}
```

### 자동차 수정 - Update Car
```http
PUT /cars/{id}
Content-Type: application/json

{
  "manufacturer": "현대",
  "model": "코나",
  "productionYear": 2024,
  "categoryIds": [1]
}
```

### 자동차 조회 - Get Car
```http
GET /cars/{id}
```

### 자동차 검색 - Search Cars
```http
GET /cars?manufacturer=현대&model=코나&categoryIds=1
```

### 자동차 상태 변경 - Change Car Availability
```http
PATCH /cars/{id}/availability?available=false
```

## 에러 코드 - Error Codes

### 에러 응답 형식 - Error Response Format
```json
{
  "code": "ERROR_CODE",
  "message": "에러 메시지"
}
```

### 에러 코드 목록 - Error Code List
* `CAR_NOT_FOUND`: 차량을 찾을 수 없음
* `INVALID_CAR_STATUS`: 잘못된 차량 상태
* `CATEGORY_NOT_FOUND`: 카테고리를 찾을 수 없음
* `CATEGORY_REQUIRED`: 카테고리 필수 입력
* `INVALID_INPUT`: 잘못된 입력값
* `INTERNAL_SERVER_ERROR`: 서버 오류
