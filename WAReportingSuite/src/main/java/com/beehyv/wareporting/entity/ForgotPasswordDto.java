package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 8/8/17.
 */
public class ForgotPasswordDto {

    private String username;

    private String captchaResponse;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaptchaResponse() {  return captchaResponse;  }

    public void setCaptchaResponse(String captchaResponse) {   this.captchaResponse = captchaResponse;  }
}
