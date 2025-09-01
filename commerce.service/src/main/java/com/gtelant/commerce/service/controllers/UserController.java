package com.gtelant.commerce.service.controllers;

import com.gtelant.commerce.service.models.User;
import com.gtelant.commerce.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//User：對應資料庫的使用者模型。
//UserRepository：資料存取物件，用來查詢、存取使用者資料。
//@Autowired：Spring 自動注入 Bean。
//ResponseEntity：用來包裝 HTTP 回應，包含狀態碼與資料。
//@RestController, @RequestMapping, @GetMapping 等：Spring MVC 的註解，用來定義 REST API。
//Optional：避免 null 值，包裝可能存在或不存在的物件。


@RestController
//代表這是一個 RESTful API 控制器。
//自動將方法回傳物件轉換成 JSON 格式。

@RequestMapping("/users")
//定義所有 API 的基礎路徑為 /users。

public class UserController {

    @Autowired
    private UserRepository userRepository;
//使用 Spring 的自動注入，將 UserRepository 注入到控制器中。
//userRepository 是操作使用者資料庫的介面。
//這樣就可以呼叫 .findAll(), .save(), .deleteById() 等方法

    // 取得所有使用者
    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
//@GetMapping：對應 HTTP GET 請求。這裡對應路徑 /users。
//Iterable<User> getAllUsers()：回傳所有使用者的集合。
//userRepository.findAll()：從資料庫取出所有使用者資料。
//邏輯：客戶端呼叫 GET /users，回傳全部使用者。

    // 依 ID 取得單一使用者
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//@GetMapping("/{id}")：對應 GET /users/{id}，路徑變數 id 會傳進方法。
//@PathVariable Integer id：將 URL 的 {id} 參數綁定到 id 變數。
//userRepository.findById(id)：從資料庫查找對應的使用者。回傳 Optional<User>。
//.map(ResponseEntity::ok)：如果找到了使用者，就包裝成 HTTP 200 OK 回傳。
//.orElse(ResponseEntity.notFound().build())：如果沒找到使用者，就回傳 HTTP 404 Not Found。
//邏輯：透過 ID 查詢使用者，並依照是否存在回傳不同 HTTP 狀態。


    // 新增使用者
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
//@PostMapping：對應 HTTP POST /users，通常用來新增資源。
//@RequestBody User user：將請求 JSON 轉成 User 物件。
//userRepository.save(user)：將 User 寫入資料庫，若是新物件會自動產生 ID。
//邏輯：客戶端傳送 JSON 使用者資料，儲存後回傳新增的完整物件（含自動生成 ID）。

    // 更新使用者
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
//@PutMapping("/{id}")：對應 HTTP PUT /users/{id}，用來更新使用者資料。
//先透過 findById(id) 查找使用者。
//如果 Optional 為空，表示資料不存在，回傳 404。

        User user = userOptional.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        user.setHasNewsletter(userDetails.getHasNewsletter());
        user.setAddress(userDetails.getAddress());
        user.setCity(userDetails.getCity());
        user.setState(userDetails.getState());
        user.setZipcode(userDetails.getZipcode());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
//取出查到的使用者物件。
//將前端送來的資料 (userDetails) 填入對應欄位。
//邏輯：
//只更新資料庫中存在的使用者。
//每個欄位都逐一更新，避免覆蓋不必要的資料。
//userRepository.save(user)：更新資料庫中的使用者。
//回傳 HTTP 200 OK，並附上更新後的使用者物件。

    // 刪除使用者
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
//@DeleteMapping("/{id}")：對應 HTTP DELETE /users/{id}，用來刪除使用者。
//userRepository.existsById(id)：先檢查該使用者是否存在。
//userRepository.deleteById(id)：刪除資料庫中的使用者。
//回傳 HTTP 204 No Content 表示刪除成功。

//Note
//GET /users → 取得全部使用者
//GET /users/{id} → 取得指定使用者
//POST /users → 新增使用者
//PUT /users/{id} → 更新使用者
//DELETE /users/{id} → 刪除使用者

//設計邏輯：
//使用 UserRepository 操作資料庫。
//對每個 CRUD 操作做 存在性檢查（Optional / existsById）。
//使用 ResponseEntity 回傳正確 HTTP 狀態碼，符合 RESTful API 標準。