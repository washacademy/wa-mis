package com.beehyv.wareporting.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Created by beehyv on 15/3/17.
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
//        char[] credentials = (char[]) info.getCredentials();
        String hashedPassword = info.getCredentials().toString();
        return BCrypt.checkpw(password, hashedPassword);
    }
}
