package com.controller;

import com.config.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.Product;
import com.model.User;
import com.model._Address;
import com.service.*;
import com.service.dto.OrderInfomationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class OrderControler {
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    OrderService orderService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    CartServices cartServices;

    @Autowired
    ProductService productService;

    @GetMapping("/order/orderProcess")
    public String orderProcess(@CookieValue("user_login") String user_login, Model model, HttpServletResponse res) throws IOException {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(user_login));

        if (cartServices.findAllLineItems(u.getUserCart().getId()).isEmpty()) {
            return "home/404";
        }

        model.addAttribute("orderInfo", new OrderInfomationDto());
        model.addAttribute("provinces", provinceService.findAll());
        return "home/orderProcess";
    }
    @ModelAttribute
    public List<Product> topSales() {
        return productService.findTopSales();
    }

    @PostMapping("/order/orderProcess")
    public String handleOrder(@CookieValue("user_login") String user_login, @ModelAttribute("orderInfo") OrderInfomationDto orderInfomationDto, Model model) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(user_login));

        model.addAttribute("provinces", provinceService.findAll());
        model.addAttribute("orderInfo", orderInfomationDto);
        orderInfomationDto.setUserId(u.getId());

        if (orderService.saveOrder(orderInfomationDto, u.getUserCart().getId()) == null) {
            //error
            model.addAttribute("orderInfo", orderInfomationDto);
            model.addAttribute("result", "error");
            model.addAttribute("message", "Server got error, code: 500");
        } else {
            //sucess
            model.addAttribute("orderInfo", new OrderInfomationDto());
            model.addAttribute("result", "success");
            model.addAttribute("message", "Order successfully! You will be redirect home after 3s..");
        }
        return "home/orderProcess";
    }

    @GetMapping("order/my_orders")
    public String myOrders(@CookieValue("user_login") String user_login, Model model) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(user_login));

        model.addAttribute("myOrders", orderService.findAllByUserId(u.getId()));
        return "home/userOrders";
    }

}
