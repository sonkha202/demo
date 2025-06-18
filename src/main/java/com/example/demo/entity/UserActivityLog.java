package com.example.demo.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog extends BaseEntity  {

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="activity_type", nullable= false, length=20)
    private String activityType; //login or logout 

    @Column(name="activity_time", nullable= false)
    private LocalDateTime activityTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }

}
