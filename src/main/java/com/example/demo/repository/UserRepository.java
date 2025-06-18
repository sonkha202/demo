package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.time.LocalDateTime;

@Repository
public class UserRepository implements BaseRepositoty<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) {
    String sql ="INSERT INTO users (username, password, created_by, created_at, status )VALUES (?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getCreatedBy(),
                user.getCreatedAt(),
                user.getStatus());
    }

    public void update(User user) {
       String sql="UPDATE users SET password=?, updated_by=?, updated_at=? WHERE id = ? AND status ='ACTIVE'";
       jdbcTemplate.update(sql,
       user.getPassword(),
       user.getUpdatedBy(),
       user.getUpdatedAt(),
       user.getId());
    }

    public void delete(Long id, String deletedBy) {
       String sql = " UPDATE users SET status ='DELETED', deleted_by=?, deleted_at=? WHERE id=? ";
       jdbcTemplate.update(sql, deletedBy,LocalDateTime.now(), id);
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ? AND status = 'ACTIVE'";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<User> findAll() {
        String sql ="SELECT * FROM users WHERE status ='ACTIVE'";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }

    public User findByUsername(String username) {
        String sql="SELECT * FROM users WHERE username=? AND status='ACTIVE'";
        List<User> list= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return list.isEmpty() ? null : list.get(0);
      
    }
}