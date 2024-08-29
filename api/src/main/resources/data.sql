INSERT INTO p_users (user_id, email, nickname, password, role) VALUES (1, 'USER01@gmail.com', 'USER01', 'security01', 'USER');
INSERT INTO p_users (user_id, email, nickname, password, role) VALUES (2, 'OWNER01@gmail.com', 'OWNER01', 'security02', 'OWNER');
INSERT INTO p_users (user_id, email, nickname, password, role) VALUES (3, 'MANAGER01@gmail.com', 'MANAGER01', 'security03', 'MANAGER');
INSERT INTO p_users (user_id, email, nickname, password, role) VALUES (4, 'MASTER01@gmail.com', 'MASTER01', 'security04', 'MASTER');
INSERT INTO p_users (user_id, email, nickname, password, role) VALUES (5, 'OWNER02@gmail.com', 'OWNER02', 'security05', 'OWNER');

-- INSERT INTO p_addresses (user_id, city, street, zipcode) VALUES (2, '서울시', '강남구', '12345');
-- INSERT INTO p_addresses (user_id) VALUES (2);

INSERT INTO p_restaurants (restaurant_id, user_id, content, name, phone) VALUES ('5e9d300f-034a-466c-81bc-eafd637e2292', 2,  '광화문1호점', '호식이 두마리 치킨', '02-777-7777');
INSERT INTO p_restaurants (restaurant_id, user_id, content, name, phone) VALUES ('5e9d300f-034a-466c-81bc-eafd637e2293',  2, '광화문2호점', '호식이 두마리 치킨', '02-777-7777');
INSERT INTO p_restaurants (restaurant_id, user_id, content, name, phone) VALUES ('5e9d300f-034a-466c-81bc-eafd637e2294', 5,  '광화문 최고 맛집', '홍콩반점', '02-1199-1100');

INSERT INTO p_menus (price, menu_id, restaurant_id, content, name) VALUES (16000, '123d300f-034a-466c-81bc-eafd637e2292', '5e9d300f-034a-466c-81bc-eafd637e2292', '양념치킨', '양념치킨');
INSERT INTO p_menus (price, menu_id, restaurant_id, content, name) VALUES (15000, '124d300f-034a-466c-81bc-eafd637e2292', '5e9d300f-034a-466c-81bc-eafd637e2292', '후라이드치킨', '후라이드치킨');
