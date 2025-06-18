package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserActivityLog;
import com.example.demo.repository.UserActivityLogRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivityLogRepository userActivityLogRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createUser(User user, String createdBy) {
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(createdBy);
        user.setStatus("ACTIVE");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user, String updatedBy) {
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(updatedBy);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.update(user);
    }

    public void deleteUser(Long id, String deletedBy) {
        userRepository.delete(id, deletedBy);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }
        // login history save
        UserActivityLog log = new UserActivityLog();

        log.setActivityTime(LocalDateTime.now());
        log.setUserId(user.getId());
        log.setActivityType("LOGIN");
        log.setCreatedBy(user.getUsername());
        log.setCreatedAt(LocalDateTime.now());
        log.setStatus("ACTIVE");

        userActivityLogRepository.save(log);

        return user;
    }

    public String logout(Long userId, String username) {
        UserActivityLog log = new UserActivityLog();

        log.setActivityTime(LocalDateTime.now());
        log.setUserId(userId);
        log.setActivityType("LOGOUT");
        log.setCreatedBy(username);
        log.setCreatedAt(LocalDateTime.now());
        log.setStatus("ACTIVE");
        userActivityLogRepository.save(log);

        return "Đăng xuất thành công";
    }
}
