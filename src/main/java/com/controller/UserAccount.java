package com.controller;

import com.model.UserLogin;
import com.model.UserRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAccount {


    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("user", new UserLogin());
        System.out.println("login");
        return "login";
    }

    @PostMapping("login")
    public String loginHandle(@ModelAttribute("user") UserLogin userLogin, Model model) {
        System.out.println("userLogin");
        System.out.println(userLogin);

        model.addAttribute("user", null);
        return "login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegister());
        return "register";
    }

    @PostMapping("register")
    public String registerHandle(@ModelAttribute("user") UserRegister userRegister) {
        System.out.println("register");
        System.out.println(userRegister);
        return "register";
    }
}
