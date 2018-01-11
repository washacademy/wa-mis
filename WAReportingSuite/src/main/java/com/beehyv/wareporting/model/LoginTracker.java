package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "login_tracker")
public class LoginTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11)")
    private Integer loginId;

    @Column(name = "user_id", columnDefinition = "INT(11)")
    private Integer userId;

    @Column(name = "login_time", columnDefinition = "TIMESTAMP")
    private Date loginTime;

    @Column(name = "login_successful", columnDefinition = "BIT(1)")
    private boolean loginSuccessful;


    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }
}
