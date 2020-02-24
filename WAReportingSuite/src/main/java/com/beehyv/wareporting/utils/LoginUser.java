package com.beehyv.wareporting.utils;

/**
 * Created by beehyv on 21/3/17.
 */
public class LoginUser {
    private String username;

    private String password;

    private boolean rememberMe;

    private String cipherTextHex;

    private String saltHex;

    private String fromUrl;

    private String captchaResponse;

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }

    public LoginUser() {
    }

    public LoginUser(String password) {
        this.password = password;
    }

    public LoginUser(String cipherTextHex, String saltHex) {
        this.cipherTextHex = cipherTextHex;
        this.saltHex = saltHex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getCipherTextHex() {    return cipherTextHex;   }

    public void setCipherTextHex(String cipherTextHex) {
        this.cipherTextHex = cipherTextHex;
    }

    public String getSaltHex() {
        return saltHex;
    }

    public void setSaltHex(String saltHex) {
        this.saltHex = saltHex;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }
}
