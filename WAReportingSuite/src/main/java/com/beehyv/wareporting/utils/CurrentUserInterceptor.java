package com.beehyv.wareporting.utils;

import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by beehyv on 21/3/17.
 */
@Component
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // Add the current user into the request
        User currentUser = userService.getCurrentUser();
        if( currentUser != null ) {
            httpServletRequest.setAttribute( "currentUser", currentUser );
        }
    }
}
