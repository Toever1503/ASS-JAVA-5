package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.Authority;
import com.model.User;
import com.model.UserLogin;
import com.model.UserRegister;
import com.service.AuthorityService;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserAccount {

    @Autowired
    public UserService service;

    @Autowired
    public AuthorityService authorityService;

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("user", new UserLogin());
        System.out.println("login");
        return "login";
    }

    @PostMapping("login")
    public String loginHandle(@ModelAttribute("user") UserLogin userLogin, Model model, HttpServletRequest req) {
        System.out.println("userLogin");
        System.out.println(userLogin);

        User oldUser = service.findByEmailOrUsername(userLogin.getUsername(), userLogin.getUsername());
        if (oldUser == null) {
            model.addAttribute("user", userLogin);
            model.addAttribute("result", null);
            model.addAttribute("message", "username or password invalid");
        } else {
            if (userLogin.getPassword().equals(oldUser.getPassword())) {
                // success
                model.addAttribute("user", new UserLogin());
                model.addAttribute("result", "success");
                model.addAttribute("message", "Login successfully! You will be redirect to home");
                model.addAttribute("user", null);
                HttpSession session = req.getSession();
                session.setAttribute("loginUser", oldUser);
            } else {
                //error
                model.addAttribute("user", userLogin);
                model.addAttribute("result", null);
                model.addAttribute("message", "username or password invalid");
            }
        }
        return "login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegister());
        return "register";
    }

    @PostMapping("register")
    public String registerHandle(@ModelAttribute("user") UserRegister userRegister, Model model, BindingResult result) {
        System.out.println("register->errors:"+result.getErrorCount());
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
                u.setPassword(userRegister.getPassword());
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
}
