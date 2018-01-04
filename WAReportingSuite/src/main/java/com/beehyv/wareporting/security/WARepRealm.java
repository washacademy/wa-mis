package com.beehyv.wareporting.security;

import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by beehyv on 15/3/17.
 */
public class WARepRealm extends JdbcRealm {

    @Autowired
    private UserService userService;

    public WARepRealm() {
        setName("waRepRealm");
        setCredentialsMatcher(new BCryptCredentialsMatcher());
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUserByUsername(usernamePasswordToken.getUsername());
        if(user != null) {
            return new SimpleAuthenticationInfo(user.getUserId(), user.getPassword(), getName());
        } else {
            return null;
        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer userId = (Integer) principals.fromRealm(getName()).iterator().next();
        User user = userService.findUserByUserId(userId);
        if(user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(user.getRoleName());
            info.addStringPermission(userService.getRoleById(user.getRoleId()).getPermissionId().getPermission());
            return info;
        } else {
            return null;
        }
    }
}
