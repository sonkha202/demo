package com.example.demo.repository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserActivityLog;

@Repository
public class UserActivityLogRepository implements BaseRepositoty<UserActivityLog>  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(UserActivityLog log) {
        String sql ="INSERT INTO user_activity_logs (user_id, activity_type, activity_time, created_by, created_at, status) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, log.getUserId(),
        log.getActivityType(),
        log.getActivityTime(),
        log.getCreatedBy(),
        log.getCreatedAt(),
        log.getStatus() );
    }
    @Override
    public void delete(Long id, String deletedBy) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<UserActivityLog> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserActivityLog findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(UserActivityLog log) {
        // TODO Auto-generated method stub
        
    }
    
}
