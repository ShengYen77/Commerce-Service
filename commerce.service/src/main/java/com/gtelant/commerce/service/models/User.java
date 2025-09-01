package com.gtelant.commerce.service.models;

import jakarta.persistence.*;
//引入 JPA (Jakarta Persistence API) 相關的註解，例如 @Entity, @Table, @Id 等，用於定義資料表和欄位的對應。

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//引入 Lombok 提供的註解，減少樣板程式碼：
//@Data：自動產生 Getter、Setter、toString()、hashCode()、equals()。
//@NoArgsConstructor：產生無參數建構子。
//@AllArgsConstructor：產生包含所有欄位的建構子。

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
//引入 Java 8+ 的日期時間 API 以及集合類別：
//LocalDate：只包含日期（年月日）。
//LocalDateTime：包含日期和時間。
//List：儲存多個 UserSegment 的集合。

@Entity
//標記這個類別為 JPA 實體，會對應到資料庫中的一張表格。
@Table(name = "users")
//指定資料表名稱為 users。
//如果不加這個註解，預設會用類別名稱 User 作為表名。
@Data
@NoArgsConstructor
@AllArgsConstructor
//使用 Lombok 簡化程式碼，產生常見的建構子和方法。
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//@Id：指定這是主鍵欄位。
//@GeneratedValue(strategy = GenerationType.IDENTITY)：使用資料庫的 自動遞增 (auto-increment) 方式產生主鍵。
//private int id;：對應到 users 表的 id 欄位。
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;
//對應 email 欄位，並加上 unique = true，表示這個欄位必須唯一，不能重複。
    @Column(name = "birthday")
    private LocalDate birthday;
//對應 birthday 欄位，用 LocalDate 存生日（只記錄年月日）。
    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "password")
    private String password;
//對應 password 欄位，用來存放使用者密碼（通常會存 加密後的值，不會明文存放）。
    @Column(name = "role")
    private String role;
//對應 role 欄位，用來表示使用者角色（例如 admin, user 等）。
    @Column(name = "has_newsletter")
    private String hasNewsletter;
//對應 has_newsletter 欄位，存放使用者是否訂閱電子報。
//這裡設計為 String，可能存 "Y" / "N" 或 "true" / "false"。
    @Column(name = "last_seen_at")
    private LocalDateTime lastSeenAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user")
    private List<UserSegment> userSegments;
//@OneToMany：定義 一對多關聯，一個 User 可以有多個 UserSegment。
//mappedBy = "user"：表示 UserSegment 這個實體裡面有個 user 屬性是關聯的外鍵。
//private List<UserSegment> userSegments;：在程式中用 List 存放關聯的多個 UserSegment。
}