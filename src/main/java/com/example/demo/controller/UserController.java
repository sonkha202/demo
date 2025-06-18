package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("userId") != null && session.getAttribute("username") != null;
    }

    private void doLogout(HttpSession session) {
        session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest, HttpSession session) {

        try {
            if (isLoggedIn(session)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Đăng nhập rồi");
            }
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return ResponseEntity.ok("Đăng nhập thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session.getAttribute("userId") == null || session.getAttribute("username") == null) {
            return ResponseEntity.badRequest().body("Chưa đăng nhập");
        }
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        String result = userService.logout(userId, username);
        doLogout(session);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user, HttpSession session) {

        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        String currentUser = (String) session.getAttribute("username");
        userService.createUser(user, currentUser);
        return ResponseEntity.ok("thêm thành công");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        String currentUser = (String) session.getAttribute("username");
        Long userId = (Long) session.getAttribute("userId");
        userService.updateUser(user, currentUser);
        userService.logout(userId, currentUser);
        doLogout(session);
        return ResponseEntity.ok("Sửa thành công, hãy đăng nhập lại");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        String currentUser = (String) session.getAttribute("username");
        userService.deleteUser(id, currentUser);
        return ResponseEntity.ok("xóa thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyid(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("User tìm được: " + user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getall(HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    //kkk
}
