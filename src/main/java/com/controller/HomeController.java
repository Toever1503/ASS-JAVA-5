package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping
    public String home(){
        return "home/home";
    }


    @RequestMapping("category/{catName}")
    public String category(@PathVariable("catName") String catName, Model model) {
        return "category";
    }
}
