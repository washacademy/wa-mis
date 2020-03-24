package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.business.LoginTrackerService;
import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.entity.BasicValidationResult;
import com.beehyv.wareporting.model.LoginTracker;
import com.beehyv.wareporting.model.User;
import com.beehyv.wareporting.utils.LoginUser;
import com.beehyv.wareporting.utils.LoginValidator;
import com.captcha.botdetect.web.servlet.SimpleCaptcha;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.beehyv.wareporting.utils.Global.retrieveUiAddress;
import static com.beehyv.wareporting.utils.CryptoService.decrypt;
/**
 * Created by beehyv on 15/3/17.
 */
@Controller
public class LoginController extends HttpServlet{

    private LoginValidator validator = new LoginValidator();

    @Autowired
    private LoginTrackerService loginTrackerService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/wa", "/wa/login"}, method = RequestMethod.GET)
    protected String returnLoginView(Model model, @ModelAttribute LoginUser loginUser) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        ensureUserIsLoggedOut();
        return "redirect:"+ retrieveUiAddress() +"login";
    }

    @ResponseBody
    @RequestMapping(value={"/wa/login"}, method= RequestMethod.POST)
    public String login(@RequestBody LoginUser loginUser, BindingResult errors, HttpServletResponse response) throws Exception {


            User user=userService.findUserByUsername(loginUser.getUsername());

            if(user ==  null){
                return   retrieveUiAddress() + "login?error";
            }

            if (user!= null && user.getUnSuccessfulAttempts() == null) {
                user.setUnSuccessfulAttempts(0);
                userService.setUnSuccessfulAttemptsCount(user.getUserId(), 0);
            }

            if (user!= null && user.getUnSuccessfulAttempts() == 3) {
                Date lastLogin = loginTrackerService.getLastLoginTime(user.getUserId());
                if (lastLogin != null) {
                    long diff = TimeUnit.DAYS.convert(new Date().getTime() - lastLogin.getTime(), TimeUnit.MILLISECONDS);
                    if ( diff >= 1) {
                        user.setUnSuccessfulAttempts(0);
                        userService.setUnSuccessfulAttemptsCount(user.getUserId(), 0);
                    }

                }
            }
            if (user!= null && (user.getUnSuccessfulAttempts() < 3 || user.getUnSuccessfulAttempts() == null)) {
                validator.validate(loginUser, errors);
                if (errors.hasErrors()) {
                    ensureUserIsLoggedOut();
                    return "redirect:" + retrieveUiAddress() + "login?error";
                }
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(), decrypt(loginUser), loginUser.isRememberMe());
                try {
                    ensureUserIsLoggedOut();
                    subject.login(token);
                    user.setUnSuccessfulAttempts(0);
                    LoginTracker loginTracker = new LoginTracker();
                    loginTracker.setUserId(user.getUserId());
                    loginTracker.setLoginSuccessful(true);
                    loginTracker.setLoginTime(new Date());
                    loginTrackerService.saveLoginDetails(loginTracker);
                    if(user.getDefault() == null){
                        user.setDefault(true);
                    }
                    if(user.getDefault()){
                        return "redirect:"+ retrieveUiAddress() +"changePassword";
                    }
                    userService.setLoggedIn();
                    return "redirect:"+ retrieveUiAddress() +"reports";
                } catch (AuthenticationException e) {
                    errors.reject("error.login.generic", "Invalid username or password.  Please try again.");
                    LoginTracker loginTracker = new LoginTracker();
                    if ((user) != null) {
                        userService.setUnSuccessfulAttemptsCount(user.getUserId(), null);
                        loginTracker.setUserId(user.getUserId());
                        loginTracker.setLoginSuccessful(false);
                        loginTracker.setLoginTime(new Date());
                        loginTrackerService.saveLoginDetails(loginTracker);
                        return "redirect:" + retrieveUiAddress() + "login?error";
                    }

                }

            } else {
                return "redirect:" + retrieveUiAddress() + "login?blocked";
            }

        return null;
    }

    @RequestMapping(value = {"/wa/index"}, method = RequestMethod.GET)
    protected String returnHomeView(Model model) {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return "redirect:"+ retrieveUiAddress() +"userManagement";
    }

    @RequestMapping(value = {"/wa/loginDummy"}, method = RequestMethod.GET)
    protected @ResponseBody LoginUser dummy() {
//        System.out.println("\n\n" + SecurityUtils.getSubject().getSession()+ "!!!!!!!!!!!\n\n");
        return new LoginUser();
    }

    @Override
    @RequestMapping(value={"/wa/captcha"}, method= RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        response.setContentType("application/json; charset=utf-8");


        JsonParser parser = new JsonParser();
        JsonObject formDataObj = (JsonObject) parser.parse(request.getReader());
        String captchaId = formDataObj.get("captchaId").getAsString();
        String encryptedCaptchaCode = formDataObj.get("captchaCode").getAsString();
        String captchaCode = null;
        try {
            captchaCode = decrypt(encryptedCaptchaCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // validate captcha
        SimpleCaptcha captcha = SimpleCaptcha.load(request);
        boolean isHuman = captcha.validate(captchaCode, captchaId);
       // the object that stores validation result
        BasicValidationResult validationResult = new BasicValidationResult();
        validationResult.setSuccess(isHuman);

        try {
            // write the validation result as json string for sending it back to client
            out.write(gson.toJson(validationResult));
        } catch(Exception ex) {
            out.write(ex.getMessage());
        } finally {
            out.close();
        }

    }


    private void ensureUserIsLoggedOut() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if(currentUser == null) {
                return;
            }

            currentUser.logout();
            Session session = currentUser.getSession(false);
            if(session == null) {
                return;
            }
            session.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}