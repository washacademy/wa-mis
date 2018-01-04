package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static com.beehyv.wareporting.utils.Global.retrieveUiAddress;

/**
 * Created by beehyv on 21/3/17.
 */
@Controller
@RequestMapping("/wa/logout")
public class LogoutController extends AbstractController{

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:"+ retrieveUiAddress() +"login");
    }
}
