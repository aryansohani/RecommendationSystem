# 🛒 SmartBuy – Product Comparison & Recommendation System

SmartBuy is a Java-based web application built using **JSP, Servlets, and MySQL**.  
It allows users to browse products, compare prices across multiple websites, and receive recommendations based on their interactions (likes/clicks).

This project is suitable for **Final Year / Mini Project** submissions.

---

## 🚀 Features

### ✅ Core Features
- User login & logout
- Product listing with category & brand
- Search products by name
- Filter products by category
- View best deal (lowest price)
- Open product on official website
- Like (❤️) a product (click tracking)
- Click-based popularity tracking

### 🔮 Recommendation Logic (Implemented at DB + Servlet level)
- ⭐ Personalized recommendations (based on liked categories)
- 🔥 Popular products (most clicked)
- 👥 Users also clicked (extendable)

---

## 🧰 Tech Stack

- **Frontend:** JSP, HTML, CSS
- **Backend:** Java Servlets
- **Database:** MySQL
- **Server:** Apache Tomcat
- **Version Control:** Git & GitHub

---

## 📁 Project Structure

SmartBuy/
│
├── src/com/aryan/
│ ├── Product.java
│ ├── ProductServlet.java
│ ├── ClickServlet.java
│ ├── LoginServlet.java
│ ├── LogoutServlet.java
│
├── WebContent/
│ ├── login.jsp
│ ├── products.jsp
│ ├── compare.jsp
│ ├── recommended.jsp
│
├── database/
│ └── smartbuy.sql
│
└── README.md


---

## 🗄️ Database Setup (Complete SQL)

### 🔴 Reset Database
```sql
DROP DATABASE IF EXISTS smartbuy_db;
CREATE DATABASE smartbuy_db;
USE smartbuy_db;
👤 Users
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
📦 Products
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(150),
    category VARCHAR(50),
    brand VARCHAR(100)
);

INSERT INTO products (product_name, category, brand) VALUES
('iPhone 14','Mobile','Apple'),
('iPhone 15','Mobile','Apple'),
('Galaxy S23','Mobile','Samsung'),
('Galaxy S24','Mobile','Samsung'),
('OnePlus 12','Mobile','OnePlus'),
('Pixel 8','Mobile','Google'),
('MacBook Air M1','Laptop','Apple'),
('Dell XPS 13','Laptop','Dell'),
('HP Pavilion','Laptop','HP'),
('Sony WH-1000XM5','Headphone','Sony'),
('AirPods Pro','Headphone','Apple'),
('Boat Rockerz 550','Headphone','Boat'),
('Samsung Crystal 4K','TV','Samsung'),
('LG OLED C2','TV','LG'),
('Nike Air Force 1','Shoes','Nike'),
('Adidas Ultraboost','Shoes','Adidas');
💰 Product Listings (Price Comparison)
CREATE TABLE product_listings (
    listing_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    website VARCHAR(50),
    price DECIMAL(10,2),
    product_link TEXT,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE
);
🔗 Product Listings with Real Links
INSERT INTO product_listings VALUES
(NULL,1,'Amazon',69999,'https://www.amazon.in/Apple-iPhone-14-128GB-Blue/dp/B0BDJ6F5NB'),
(NULL,1,'Flipkart',67999,'https://www.flipkart.com/apple-iphone-14-blue-128-gb/p/itm0b1f4f'),
(NULL,1,'Croma',68999,'https://www.croma.com/apple-iphone-14-blue-128gb/p/234567'),

(NULL,2,'Amazon',79999,'https://www.amazon.in/Apple-iPhone-15-128GB-Blue/dp/B0CHX5B3D9'),
(NULL,2,'Flipkart',77999,'https://www.flipkart.com/apple-iphone-15-blue-128-gb/p/itmcd1'),

(NULL,3,'Amazon',64999,'https://www.amazon.in/Samsung-Galaxy-S23-Phantom-Black/dp/B0BTYVTCFY'),
(NULL,3,'Flipkart',63999,'https://www.flipkart.com/samsung-galaxy-s23-phantom-black-128-gb/p/itm4d0'),

(NULL,7,'Amazon',82999,'https://www.amazon.in/Apple-MacBook-Chip-13-inch/dp/B08N5W4NNB'),

(NULL,10,'Amazon',29999,'https://www.amazon.in/Sony-WH-1000XM5-Cancelling-Headphones/dp/B09XS7JWHH'),

(NULL,13,'Amazon',42999,'https://www.amazon.in/Samsung-Crystal-4K-Smart-TV/dp/B09TV'),

(NULL,15,'Amazon',7499,'https://www.amazon.in/Nike-Air-Force-1/dp/B09AF1');
🖱️ User Clicks (Likes / Rating System)
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
📊 Useful Queries
🔥 Popular Products
SELECT p.product_name, SUM(uc.click_count) AS popularity
FROM products p
JOIN user_clicks uc ON p.product_id = uc.product_id
GROUP BY p.product_id
ORDER BY popularity DESC;
⭐ Recommended Products (User-Based)
SELECT DISTINCT p.*
FROM products p
JOIN user_clicks uc ON p.product_id = uc.product_id
WHERE uc.user_id = ?
ORDER BY uc.click_count DESC;
💸 Best Deal Per Product
SELECT p.product_name, pl.price, pl.product_link
FROM products p
JOIN product_listings pl ON p.product_id = pl.product_id
JOIN (
    SELECT product_id, MIN(price) min_price
    FROM product_listings
    GROUP BY product_id
) mp ON pl.product_id = mp.product_id
AND pl.price = mp.min_price;
▶️ How to Run
Clone repository

git clone https://github.com/your-username/smartbuy.git
Import into Eclipse / IntelliJ

Configure Apache Tomcat

Run SQL in MySQL

Start server

Open:

http://localhost:8080/SmartBuy/login
🎓 Academic Value
Normalized relational database

Click-based recommendation system

Price comparison logic

MVC architecture

Real-world e-commerce use case

👨‍💻 Author
SmartBuy
Final Year Engineering Project
Built using Java, JSP, Servlets & MySQL


---

If you want, next I can:
- ✅ Guide you to **push this to GitHub**
- 🧠 Write **viva explanations**
- 📸 Add **screenshots section**
- 📄 Convert this into **project report chapters**

Just say the word 👌
