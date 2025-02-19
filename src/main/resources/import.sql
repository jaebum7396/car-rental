INSERT INTO category (name, created_at, updated_at) VALUES ('미니SUV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category (name, created_at, updated_at) VALUES ('준중형 SUV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category (name, created_at, updated_at) VALUES ('대형RV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category (name, created_at, updated_at) VALUES ('중형 트럭', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category (name, created_at, updated_at) VALUES ('경형 RV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category (name, created_at, updated_at) VALUES ('중형 SUV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('현대', '코나', 2024, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('현대', '아이오닉', 2024, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('현대', '스타리아', 2022, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('현대', '포터', 2024, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('현대', '투싼', 2023, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('KIA', '카니발', 2021, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('KIA', '레이', 2022, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('KIA', '봉고3', 2023, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO car (manufacturer, model, production_year, available, created_at, updated_at) VALUES ('KIA', '쏘렌토', 2024, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO car_category (car_id, category_id) VALUES (1, 1);
INSERT INTO car_category (car_id, category_id) VALUES (2, 2);
INSERT INTO car_category (car_id, category_id) VALUES (3, 3);
INSERT INTO car_category (car_id, category_id) VALUES (4, 4);
INSERT INTO car_category (car_id, category_id) VALUES (5, 2);
INSERT INTO car_category (car_id, category_id) VALUES (6, 3);
INSERT INTO car_category (car_id, category_id) VALUES (7, 5);
INSERT INTO car_category (car_id, category_id) VALUES (8, 4);
INSERT INTO car_category (car_id, category_id) VALUES (9, 6);