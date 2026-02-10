-- ============================
-- 🔴 RESET DATABASE
-- ============================
DROP DATABASE IF EXISTS smartbuy_db;
CREATE DATABASE smartbuy_db;
USE smartbuy_db;
SELECT * FROM user_clicks;
-- ============================
-- 👤 USERS
-- ============================

select * from users;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (name,email,password) VALUES
('User A','a@test.com','123'),
('User B','b@test.com','123');

-- ============================
-- 📦 PRODUCTS
-- ============================
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(150),
    category VARCHAR(50),
    brand VARCHAR(100)
);

INSERT INTO products (product_name, category, brand) VALUES
-- Mobiles
('iPhone 14','Mobile','Apple'),
('iPhone 15','Mobile','Apple'),
('Galaxy S23','Mobile','Samsung'),
('Galaxy S24','Mobile','Samsung'),
('OnePlus 12','Mobile','OnePlus'),
('Pixel 8','Mobile','Google'),

-- Laptops
('MacBook Air M1','Laptop','Apple'),
('Dell XPS 13','Laptop','Dell'),
('HP Pavilion','Laptop','HP'),

-- Headphones
('Sony WH-1000XM5','Headphone','Sony'),
('AirPods Pro','Headphone','Apple'),
('Boat Rockerz 550','Headphone','Boat'),

-- TV
('Samsung Crystal 4K','TV','Samsung'),
('LG OLED C2','TV','LG'),

-- Shoes
('Nike Air Force 1','Shoes','Nike'),
('Adidas Ultraboost','Shoes','Adidas');

-- ============================
-- 💰 PRODUCT LISTINGS
-- ============================
CREATE TABLE product_listings (
    listing_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    website VARCHAR(50),
    price DECIMAL(10,2),
    product_link TEXT,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE
);

-- ============================
-- 🔗 LISTINGS WITH REAL LINKS
-- ============================

-- iPhone 14 (id = 1)
INSERT INTO product_listings VALUES
(NULL,1,'Amazon',69999,'https://www.amazon.in/Apple-iPhone-14-128GB-Blue/dp/B0BDJ6F5NB'),
(NULL,1,'Flipkart',67999,'https://www.flipkart.com/apple-iphone-14-blue-128-gb/p/itm0b1f4f'),
(NULL,1,'Croma',68999,'https://www.croma.com/apple-iphone-14-blue-128gb/p/234567');

-- iPhone 15 (id = 2)
INSERT INTO product_listings VALUES
(NULL,2,'Amazon',79999,'https://www.amazon.in/Apple-iPhone-15-128GB-Blue/dp/B0CHX5B3D9'),
(NULL,2,'Flipkart',77999,'https://www.flipkart.com/apple-iphone-15-blue-128-gb/p/itmcd1'),
(NULL,2,'Croma',78999,'https://www.croma.com/apple-iphone-15-blue-128gb/p/245678');

-- Galaxy S23 (id = 3)
INSERT INTO product_listings VALUES
(NULL,3,'Amazon',64999,'https://www.amazon.in/Samsung-Galaxy-S23-Phantom-Black/dp/B0BTYVTCFY'),
(NULL,3,'Flipkart',63999,'https://www.flipkart.com/samsung-galaxy-s23-phantom-black-128-gb/p/itm4d0'),
(NULL,3,'Reliance Digital',64599,'https://www.reliancedigital.in/samsung-galaxy-s23/p/491996864');

-- MacBook Air M1 (id = 7)
INSERT INTO product_listings VALUES
(NULL,7,'Amazon',82999,'https://www.amazon.in/Apple-MacBook-Chip-13-inch/dp/B08N5W4NNB'),
(NULL,7,'Flipkart',80999,'https://www.flipkart.com/apple-macbook-air-m1/p/itm3'),
(NULL,7,'Croma',81999,'https://www.croma.com/apple-macbook-air-m1/p/223344');

-- Sony WH-1000XM5 (id = 10)
INSERT INTO product_listings VALUES
(NULL,10,'Amazon',29999,'https://www.amazon.in/Sony-WH-1000XM5-Cancelling-Headphones/dp/B09XS7JWHH'),
(NULL,10,'Flipkart',28999,'https://www.flipkart.com/sony-wh-1000xm5/p/itm2'),
(NULL,10,'Croma',29599,'https://www.croma.com/sony-wh-1000xm5/p/334455');

-- Samsung Crystal 4K (id = 13)
INSERT INTO product_listings VALUES
(NULL,13,'Amazon',42999,'https://www.amazon.in/Samsung-Crystal-4K-Smart-TV/dp/B09TV'),
(NULL,13,'Flipkart',40999,'https://www.flipkart.com/samsung-crystal-4k-tv/p/itm2'),
(NULL,13,'Reliance Digital',41999,'https://www.reliancedigital.in/samsung-crystal-4k-tv/p/491998');

-- Nike Air Force 1 (id = 15)
INSERT INTO product_listings VALUES
(NULL,15,'Amazon',7499,'https://www.amazon.in/Nike-Air-Force-1/dp/B09AF1'),
(NULL,15,'Flipkart',7299,'https://www.flipkart.com/nike-air-force-1/p/itm5');

-- ============================
-- 🖱️ USER CLICKS
-- ============================
CREATE TABLE user_clicks (
    user_id INT,
    product_id INT,
    click_count INT DEFAULT 1,
    last_clicked TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO user_clicks VALUES
(1,1,2,NOW()),
(1,3,1,NOW()),
(2,1,1,NOW()),
(2,10,1,NOW());

-- ============================
-- ✅ FINAL CHECK
-- ============================
SELECT p.product_name, pl.website, pl.price, pl.product_link
FROM products p
JOIN product_listings pl ON p.product_id = pl.product_id
ORDER BY p.product_name, pl.price;

SELECT
    p.product_id,
    p.product_name,
    p.brand,
    pl.price,
    pl.product_link
FROM products p
JOIN product_listings pl 
    ON p.product_id = pl.product_id
JOIN (
    SELECT product_id, MIN(price) AS min_price
    FROM product_listings
    GROUP BY product_id
) mp
    ON pl.product_id = mp.product_id
   AND pl.price = mp.min_price
WHERE p.category = ?
LIMIT 10;
