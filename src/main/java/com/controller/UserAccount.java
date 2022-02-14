package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAccount {

    @GetMapping("login")
    public String login() {
        System.out.println("login");
        return "login";
    }

    @PostMapping("login")
    public String loginHandle() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        System.out.println("register");
        return "register";
    }

    @PostMapping("register")
    public String registerHandle() {
        return "register";
    }
}
