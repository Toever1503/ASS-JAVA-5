package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.config.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.*;
import com.service.AuthorityService;
import com.service.ProductService;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


@Controller
public class UserAccount {

    @Autowired
    public UserService service;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorityService authorityService;

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ProductService productService;

    @GetMapping("login")
    public String login(@CookieValue(name = "user_login", required = false) String user_login, Model model) {
        if (user_login != null) {
            if (Calendar.getInstance().getTimeInMillis() < jwtTokenUtil.getExpirationDateFromToken(user_login).getTime()) {
                return "redirect:/";
            }
        } else {
            model.addAttribute("user", new UserLogin());
            System.out.println("login");
        }
        return "login";
    }

    @PostMapping("login")
    public String loginHandle(@ModelAttribute("user") UserLogin userLogin, Model model, HttpServletResponse res) {
        System.out.println("userLogin");

        User oldUser = service.findByEmailOrUsername(userLogin.getUsername(), userLogin.getUsername());
        if (oldUser == null) {
            System.out.println("not found");
            model.addAttribute("user", userLogin);
            model.addAttribute("result", "error");
            model.addAttribute("message", "username or password invalid");
        } else {
            if (BCrypt.checkpw(userLogin.getPassword(), oldUser.getPassword())) {
                // success
                System.out.println("login succcess");
                model.addAttribute("user", new UserLogin());
                model.addAttribute("result", "success");
                model.addAttribute("message", "Login successfully! You will be redirect to home");
                String token = jwtTokenUtil.generateToken(new org.springframework.security.core.userdetails.User(oldUser.getUsername(), "null", Collections.emptyList()));
                Cookie userCookie = new Cookie("user_login", token);
                if (userLogin.getIsRemember())
                    userCookie.setMaxAge((int) JwtTokenUtil.JWT_TOKEN_VALIDITY);
                res.addCookie(userCookie);
            } else {
                //error
                model.addAttribute("user", userLogin);
                model.addAttribute("result", "error");
                model.addAttribute("message", "username or password invalid");
            }
        }
        return "login";
    }
    @ModelAttribute
    public List<Product> topSales() {
        return productService.findTopSales();
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegister());
        return "register";
    }

    @PostMapping("register")
    public String registerHandle(@ModelAttribute("user") UserRegister userRegister, Model model, BindingResult result) {
        System.out.println("register->errors:" + result.getErrorCount());
        if (result.getFieldErrorCount() == 0) {
            System.out.println(userRegister);
            User oldUser = service.findByEmailOrUsername(userRegister.getEmail(), userRegister.getUsername());
            if (oldUser != null) {
                model.addAttribute("user", userRegister);
                model.addAttribute("result", "error");
                if (oldUser.getUsername().equalsIgnoreCase(userRegister.getUsername())) {
                    model.addAttribute("message", "Username has existed!");
                } else if (oldUser.getEmail().equalsIgnoreCase(userRegister.getEmail())) {
                    model.addAttribute("message", "Email has existed!");
                }
            } else {
                User u = new User();
                u.setUsername(userRegister.getUsername());
                u.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                u.setName(userRegister.getFullname());
                u.setEmail(userRegister.getEmail());

                if (service.save(u) != null) {
                    model.addAttribute("user", new UserRegister());
                    model.addAttribute("result", "success");
                    model.addAttribute("message", "Add new successfully!");
                } else {
                    model.addAttribute("user", userRegister);
                    model.addAttribute("result", null);
                    model.addAttribute("message", "Server got error: code: 500");
                }
            }
        }
        return "register";
    }

    @GetMapping("edit_profile")
    public String editProfile(@CookieValue("user_login") String user_login, Model model) {
        String username = jwtTokenUtil.getUsernameFromToken(user_login);

        UserRegister u = new UserRegister();
        User currentUser = service.findByEmailOrUsername(username, username);

        u.setEmail(currentUser.getEmail());
        u.setUsername(currentUser.getUsername());
        u.setFullname(currentUser.getName());

        model.addAttribute("user_edit", u);
        return "home/edit_profile";
    }

    @PostMapping("edit_profile")
    public String editProfile(@ModelAttribute("user_edit") UserRegister u, Model model) {
        User originalUser = service.findByUserName(u.getUsername());
        User checkUser = service.findByEmail(u.getEmail());

        if (checkUser == null) {
            originalUser.setName(u.getFullname());
            originalUser.setEmail(u.getEmail());
            User userUpdate = service.save(originalUser);
            UserRegister userUpdateDto = new UserRegister();

            userUpdateDto.setUsername(userUpdate.getUsername());
            userUpdateDto.setEmail(userUpdate.getEmail());
            userUpdateDto.setFullname(userUpdate.getName());
            model.addAttribute("user_edit", userUpdateDto);

            model.addAttribute("result", "success");
            model.addAttribute("message", "Update has successfully");
        } else {
            if (!originalUser.getId().equals(checkUser.getId())) {
                model.addAttribute("result", "error");
                model.addAttribute("message", "Email has exited");
                model.addAttribute("user_edit", u);
            }
        }

        return "home/edit_profile";
    }

    @GetMapping("change_password")
    public String changePassword(@CookieValue("user_login") String userLogin, Model model) {
        model.addAttribute("user_pass", new UserChangePassword());
        return "home/changepassword";
    }

    @PostMapping("change_password")
    public String handleChangePassword(@CookieValue("user_login") String user_login, @ModelAttribute("user_pass") UserChangePassword userChangePassword, Model model) {
        String username = jwtTokenUtil.getUsernameFromToken(user_login);
        User originalUser = service.findByUserName(username);

        if (BCrypt.checkpw(userChangePassword.getCurrentPassword(), originalUser.getPassword())) {
            if (userChangePassword.getNewPassword().equals(userChangePassword.getConfirmNewPassword())) {
                model.addAttribute("user_pass", new UserChangePassword());
                originalUser.setPassword(passwordEncoder.encode(userChangePassword.getNewPassword()));
                service.save(originalUser);
                model.addAttribute("result", "success");
                model.addAttribute("message", "Change password successfully!");
            } else {
                //error
                model.addAttribute("user_pass", userChangePassword);
                model.addAttribute("result", "error");
                model.addAttribute("message", "New password invalid!");
            }
        } else {
            //error
            model.addAttribute("user_pass", userChangePassword);
            model.addAttribute("result", "error");
            model.addAttribute("message", "Current password invalid!");
        }

        return "home/changepassword";
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse res) {
        res.addCookie(new Cookie("user_login", null));
        return "redirect:/";
    }
}
