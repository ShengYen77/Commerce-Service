# E-Commerce Admin Dashboard API 系統開發

## 專案簡述
本專案參考[React Admin Demo](https://marmelab.com/react-admin-demo)，使用 **Spring + MySQL** 開發後台 API 系統，並透過 **Swagger** 提供 API 文件。

## 專案目標
- 提供 E-Commerce 管理後台 API  
- 支援用戶、產品、訂單、銷售等核心模組  
- 提供 RESTful API，供 React-Admin 或其他前端系統串接  
- 提供自動化 API 文件 (Swagger)

## 模組規劃
- **銷售模組**
  - 訂單管理
  - 發票管理
- **產品模組**
  - 產品管理
  - 產品類別管理
- **用戶模組**
  - 用戶管理
  - 用戶標籤管理
- **評論模組**

## 專案初始化

### 預先於本地建立資料庫
```
create database commerce_db;
```
## 設定資料庫連線 DB
```
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/commerce_db
spring.datasource.username=root
spring.datasource.password=MySQL Workbench
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
## 資料表設計

**users**
| 欄位名稱            | 型別           | 說明                                 |
| --------------- | ------------ | ---------------------------------- |
| user\_id        | INT          | 使用者ID (PRIMARY KEY)                |
| first\_name     | VARCHAR(50)  | 名字 (NOT NULL)                      |
| last\_name      | VARCHAR(50)  | 姓氏 (NOT NULL)                      |
| email           | VARCHAR(100) | 電子郵件 (NOT NULL)                    |
| birthday        | DATETIME     | 生日                                 |
| address         | VARCHAR(255) | 地址                                 |
| city            | VARCHAR(100) | 城市                                 |
| state           | VARCHAR(50)  | 州／省份                               |
| zipcode         | VARCHAR(20)  | 郵遞區號                               |
| password        | VARCHAR(255) | 加密後的密碼 (NOT NULL)                  |
| role            | VARCHAR(50)  | 角色(ex: admin, customer) (NOT NULL) |
| has\_newsletter | boolean      | 是否訂閱電子報 (預設 FALSE)                 |
| last\_seen\_at  | DATETIME     | 最近登入時間                             |
| created\_at     | DATETIME     | 建立時間                               |
| deleted\_at     | DATETIME     | 刪除時間                               |

**segments**
| 欄位名稱        | 型別           | 說明                   |
| ----------- | ------------ | -------------------- |
| segment\_id | INT          | 市場區隔ID (PRIMARY KEY) |
| name        | VARCHAR(100) | 區隔名稱 (NOT NULL)      |
| description | VARCHAR(255) | 區隔描述                 |
| created\_at | DATETIME     | 建立時間                 |
| deleted\_at | DATETIME     | 刪除時間                 |
| updated\_at | DATETIME     | 更新時間                 |

**user_segments**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| user\_segment\_id | INT      | ID (PK)  |
| user\_id          | INT      | 使用者ID (FK)  |
| segment\_id       | INT      | 市場區隔ID (FK) |
| created\_at       | DATETIME | 建立時間        |
| deleted\_at       | DATETIME | 刪除時間        |

**products**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| product\_id | INT      | 產品ID (PK)  |
| name          | VARCHAR(150) | 產品名稱  |
| description       | TEXT      | 產品描述 |
| price       | DECIMAL(12,2) | 單價        |
| stock\_quantity    | INT UNSIGNED | 庫存數量(設為非負數)  |
| category\_id       | INT | 所屬類別(FK)        |
| sku       | VARCHAR(50) | 商品代號/庫存單位        |
| status       | ENUM | 商品狀態(控制上下架)        |
| created\_at       | DATETIME | 建立時間        |
| deleted\_at       | DATETIME | 刪除時間        |
| updated\_at       | DATETIME | 更新時間        |

**product_categories**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| category\_id | INT      | 類別ID (PK)  |
| name          | VARCHAR(100) | 類別名稱  |
| description   | VARCHAR(255) | 類別描述 |
| parent\_id    | INT | 父類別ID(可為NULL)(FK)(支援階層分類)   |
| created\_at       | DATETIME | 建立時間        |
| deleted\_at       | DATETIME | 刪除時間        |
| updated\_at       | DATETIME | 更新時間        |

**orders**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| order\_id | INT      | 訂單ID (PK)  |
| user\_id  | INT | 下單的用戶(FK)  |
| status   | ENUM('pending','paid','shipped','completed','cancelled') | 訂單狀態 |
| total\_amount | DECIMAL(10,2) | 總金額  |
| created\_at       | DATETIME | 建立時間        |
| updated\_at       | DATETIME | 更新時間        |

**order_items**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| order\_item\_id | INT      | 明細ID (PK)  |
| order\_id  | INT | 訂單(FK)  |
| product\_id   | INT | 產品ID(FK) |
| total\_price | DECIMAL(10,2) | 總金額  |
| quantity       | INT | 數量        |
| unit\_price    | DECIMAL(10,2) | 單價      |

**invoices**
| 欄位名稱              | 型別       | 說明          |
| ----------------- | -------- | ----------- |
| invoice\_id | INT      | 發票ID (PK)  |
| order\_id  | INT | 對應的訂單(FK)  |
| invoice\_number  | VARCHAR(50) | 發票號碼 |
| amount | DECIMAL(10,2) | 商品金額(不含稅、運費)|
| delivery\_fee  | DECIMAL(10,2) | 運費      |
| tax\_rate    | DECIMAL(5,2) | 稅率，例如5%或0.05(可存成小數或百分比) |
| tax\_amount    | DECIMAL(10,2) | 稅額 = amount * tax_rate |
| total\_amount    | DECIMAL(10,2) | 總金額 = amount + delivery_fee + tax_amount |
| issued\_date       | DATETIME | 開立時間        |
| status       | VARCHAR(20) | 發票狀態，例如'issued', 'paid', 'cancelled'    |
| created\_at       | DATETIME | 建立時間        |
| updated\_at       | DATETIME | 更新時間        |

## 設定虛擬資料(透過DBeaver操作)
```
USE commerce_db;
```
-- 假資料 Users
```
INSERT INTO users

(first_name, last_name, email, birthday, address, city, state, zipcode, password, role, has_newsletter, last_seen_at, created_at, deleted_at)

VALUES

('John', 'Doe', 'john.doe@example.com', '1985-04-12', '123 Main St', 'Taipei', 'Taipei City', '100', 'hashedpassword1', 'customer', 'Y', NOW(), NOW(), NULL),

('Jane', 'Smith', 'jane.smith@example.com', '1990-07-23', '456 Park Ave', 'Kaohsiung', 'Kaohsiung City', '800', 'hashedpassword2', 'customer', 'N', NOW(), NOW(), NULL),

('Alice', 'Wang', 'alice.wang@example.com', '1988-11-05', '789 Elm St', 'Taichung', 'Taichung City', '400', 'hashedpassword3', 'admin', 'Y', NOW(), NOW(), NULL),

('Bob', 'Chen', 'bob.chen@example.com', '1995-02-17', '321 Oak Rd', 'Tainan', 'Tainan City', '700', 'hashedpassword4', 'user', 'N', NOW(), NOW(), NULL),

('Cathy', 'Lin', 'cathy.lin@example.com', '1992-09-30', '654 Pine St', 'New Taipei', 'New Taipei City', '220', 'hashedpassword5', 'user', 'Y', NOW(), NOW(), NULL);
```
-- 假資料 Segments
```
INSERT INTO segments

(name, description, created_at, updated_at, deleted_at)

VALUES

('VIP', '高價值客戶群', NOW(), NOW(), NULL),

('Regular', '一般客戶群', NOW(), NOW(), NULL),

('New', '新加入的客戶', NOW(), NOW(), NULL),

('Churned', '流失客戶', NOW(), NOW(), NULL),

('Loyal', '忠實客戶', NOW(), NOW(), NULL);
```
-- 假資料 User_Segments
```
INSERT INTO user_segments

(user_id, segment_id, created_at, deleted_at)

VALUES

(1, 1, NOW(), NULL),  -- John Doe -> VIP

(2, 2, NOW(), NULL),  -- Jane Smith -> Regular

(3, 1, NOW(), NULL),  -- Alice Wang -> VIP

(4, 3, NOW(), NULL),  -- Bob Chen -> New

(5, 5, NOW(), NULL);  -- Cathy Lin -> Loyal
```
-- 假資料 Product_Categories
```
INSERT INTO product_categories

(category_id, name, description, parent_id, created_at, updated_at, deleted_at)

VALUES

(1, 'Electronics', 'All kinds of electronic devices', NULL, NOW(), NOW(), NULL),

(2, 'Computers & Accessories', 'Desktops, laptops, and accessories', 1, NOW(), NOW(), NULL),

(3, 'Mobile Devices', 'Smartphones, tablets, and accessories', 1, NOW(), NOW(), NULL),

(4, 'Home Appliances', 'Appliances for home use', NULL, NOW(), NOW(), NULL),

(5, 'Kitchen Appliances', 'Microwaves, blenders, and other kitchen devices', 4, NOW(), NOW(), NULL),

(6, 'Wearables', 'Smartwatches and fitness trackers', 1, NOW(), NOW(), NULL),

(7, 'Audio Devices', 'Headphones, speakers, microphones', 1, NOW(), NOW(), NULL),

(8, 'Gaming', 'Consoles, controllers, and gaming accessories', 1, NOW(), NOW(), NULL),

(9, 'Networking', 'Routers, modems, network cables', 1, NOW(), NOW(), NULL),

(10, 'Storage Devices', 'SSDs, HDDs, portable drives', 2, NOW(), NOW(), NULL);
```
-- 假資料 Products
```
INSERT INTO products

(product_id, name, description, price, stock_quantity, category_id, sku, status, created_at, updated_at, deleted_at)

VALUES

(1, 'Laptop Pro 16"', 'High-performance laptop with 16-inch display', 2399.99, 50, 2, 'LP16-001', 'active', NOW(), NOW(), NULL),

(2, 'Smartphone X', 'Latest smartphone with OLED screen', 999.99, 120, 3, 'SPX-002', 'active', NOW(), NOW(), NULL),

(3, 'Blender 3000', 'Powerful kitchen blender', 149.99, 80, 5, 'BL3000-003', 'active', NOW(), NOW(), NULL),

(4, 'Wireless Headphones', 'Noise-cancelling over-ear headphones', 199.99, 70, 7, 'WH-004', 'active', NOW(), NOW(), NULL),

(5, 'Gaming Console Z', 'Next-gen gaming console with 1TB storage', 499.99, 40, 8, 'GCZ-005', 'active', NOW(), NOW(), NULL),

(6, 'Fitness Tracker', 'Track your daily activity and sleep', 89.99, 150, 6, 'FT-006', 'active', NOW(), NOW(), NULL),

(7, 'Router X200', 'High-speed wireless router', 129.99, 60, 9, 'RTX200-007', 'active', NOW(), NOW(), NULL),

(8, 'External SSD 1TB', 'Portable 1TB SSD storage device', 159.99, 90, 10, 'SSD1TB-008', 'active', NOW(), NOW(), NULL),

(9, 'Tablet S', '10-inch tablet for work and play', 499.99, 75, 3, 'TAB-S-009', 'active', NOW(), NOW(), NULL),

(10, 'Desktop PC Ultra', 'High-end desktop computer', 1799.99, 35, 2, 'DPCU-010', 'active', NOW(), NOW(), NULL);
```
-- 假資料 Orders
```
INSERT INTO orders (order_id, user_id, status, total_amount, created_at, updated_at)

VALUES

(1, 1, 'paid', 2399.99, NOW(), NOW()),

(2, 2, 'shipped', 1199.98, NOW(), NOW()),

(3, 3, 'completed', 2149.98, NOW(), NOW()),

(4, 4, 'pending', 999.99, NOW(), NOW()),

(5, 5, 'cancelled', 689.97, NOW(), NOW());
```
-- 假資料 Order_Items
```
INSERT INTO order_items (order_item_id, order_id, product_id, quantity, unit_price)

VALUES

-- Order 1: John Doe

(1, 1, 1, 1, 2399.99), -- Laptop Pro 16

-- Order 2: Jane Smith

(2, 2, 2, 1, 999.99),  -- Smartphone X

(3, 2, 8, 1, 199.99),  -- External SSD 1TB

-- Order 3: Alice Wang

(4, 3, 10, 1, 1799.99), -- Desktop PC Ultra

(5, 3, 4, 1, 349.99),   -- Wireless Headphones

-- Order 4: Bob Chen

(6, 4, 2, 1, 999.99),   -- Smartphone X

-- Order 5: Cathy Lin

(7, 5, 7, 1, 129.99),   -- Router X200

(8, 5, 6, 2, 279.99);   -- Fitness Tracker (2件)
```
-- 新增欄位 計算總金額
```
UPDATE order_items

SET total_price = unit_price * quantity

WHERE total_price IS NULL OR total_price = 0;
```
-- 假資料 Invoices
```
INSERT INTO invoices
(order_id, invoice_number, amount, delivery_fee, tax_rate, tax_amount, total_amount, issued_date, status, created_at, updated_at)
VALUES
-- John Doe
(1, 'INV-10001', 2399.99, 50.00, 0.05, 119.99, 2569.98, NOW(), 'issued', NOW(), NOW()),

-- Jane Smith
(2, 'INV-10002', 1199.98, 30.00, 0.05, 59.99, 1289.97, NOW(), 'issued', NOW(), NOW()),

-- Alice Wang
(3, 'INV-10003', 2149.98, 40.00, 0.05, 107.50, 2297.48, NOW(), 'issued', NOW(), NOW()),

-- Bob Chen
(4, 'INV-10004', 999.99, 20.00, 0.05, 50.00, 1069.99, NOW(), 'issued', NOW(), NOW()),

-- Cathy Lin
(5, 'INV-10005', 689.97, 25.00, 0.05, 34.50, 749.47, NOW(), 'issued', NOW(), NOW()),
```
-- 假資料延伸 5 筆額外測試
```
(1, 'INV-10006', 2399.99, 50.00, 0.05, 119.99, 2569.98, NOW(), 'paid', NOW(), NOW()),
(2, 'INV-10007', 1199.98, 30.00, 0.05, 59.99, 1289.97, NOW(), 'paid', NOW(), NOW()),
(3, 'INV-10008', 2149.98, 40.00, 0.05, 107.50, 2297.48, NOW(), 'paid', NOW(), NOW()),
(4, 'INV-10009', 999.99, 20.00, 0.05, 50.00, 1069.99, NOW(), 'cancelled', NOW(), NOW()),
(5, 'INV-10010', 689.97, 25.00, 0.05, 34.50, 749.47, NOW(), 'cancelled', NOW(), NOW());
```
-- 清空 invoices 資料表(要小心使用，會刪掉全部資料)
```
TRUNCATE TABLE invoices;
```
```
INSERT INTO invoices
(order_id, invoice_number, amount, delivery_fee, tax_rate, tax_amount, total_amount, issued_date, status, created_at, updated_at)
VALUES
(1, 'INV-10001', 2399.99, 50.00, 0.05, 120.00, 2569.99, NOW(), 'issued', NOW(), NOW()),
(2, 'INV-10002', 1199.98, 30.00, 0.05, 60.00, 1289.98, NOW(), 'issued', NOW(), NOW()),
(3, 'INV-10003', 2149.98, 40.00, 0.05, 107.50, 2297.48, NOW(), 'issued', NOW(), NOW()),
(4, 'INV-10004', 999.99, 20.00, 0.05, 50.00, 1069.99, NOW(), 'issued', NOW(), NOW()),
(5, 'INV-10005', 689.97, 25.00, 0.05, 34.50, 749.47, NOW(), 'issued', NOW(), NOW());
```

