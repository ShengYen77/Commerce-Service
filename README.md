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

