package com.controller;

import com.model.Authority;
import com.model.Cart;
import com.model.Product;
import com.model.User;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrderService orderService;

    @RequestMapping
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("products")
    public String productDashboard(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        model.addAttribute("productList", productService.findAll(page));
        return "admin/products";
    }

    @GetMapping("product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("listCategories", categoryService.findAll());
        return "admin/productAdd";
    }

    @PostMapping("product/add/{cat}")
    public String handleAddPost(@ModelAttribute("product") Product product, @PathVariable("cat") Long catId, Model model) {
        System.out.println(product);
        product.setProduct_Cat(categoryService.findById(catId));
        product.setDateUpdate(Calendar.getInstance().getTime());

        product = productService.save(product);
        if (product == null) {
            //error
            model.addAttribute("product", product);
            model.addAttribute("result", "error");
            model.addAttribute("message", "Server got error, code: 500");
        } else {
            model.addAttribute("product", new Product());
            model.addAttribute("result", "success");
            model.addAttribute("message", "Add product successfully!");
        }
        return "admin/productAdd";
    }

    @GetMapping("product/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);

        if (product == null) {
            return "home/404";
        }
        model.addAttribute("product", product);
        model.addAttribute("listCategories", categoryService.findAll());
        return "admin/productEdit";
    }

    @PostMapping("product/edit/{id}/{cat}")
    public String handleEditPost(@PathVariable("id") Long id, @ModelAttribute("product") Product product, @PathVariable("cat") Long catId, Model model) {
        System.out.println(product);
        if (product.getId() != id) {
            //error
            model.addAttribute("result", "error");
            model.addAttribute("message", "Server got error, code: 500");
            model.addAttribute("product", product);
        } else {
            product.setProduct_Cat(categoryService.findById(catId));
            product.setDateUpdate(Calendar.getInstance().getTime());

            product = productService.save(product);
            if (product == null) {
                //error
                model.addAttribute("product", product);
                model.addAttribute("result", "error");
                model.addAttribute("message", "Server got error, code: 500");
            } else {
                model.addAttribute("product", new Product());
                model.addAttribute("result", "success");
                model.addAttribute("message", "Update product successfully!");
            }
        }
        return "admin/productEdit";
    }

    @GetMapping("users")
    public String userDashboard(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {

        model.addAttribute("userList", userService.findAll(page));
        return "admin/users";
    }


    @GetMapping("user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add User");
        return "admin/user_new";
    }

    @PostMapping("user/add/{role}")
    public String handleAddUser(@ModelAttribute("user") User user, @PathVariable("role") Long role, Model model) {
        System.out.println(user);

        User checkUser = userService.findByEmailOrUsername(user.getEmail(), user.getUsername());
        if (checkUser != null) {
            //error
            model.addAttribute("user", user);
            model.addAttribute("result", "error");
            if (checkUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                model.addAttribute("message", "username has existed!");
            } else if (checkUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                model.addAttribute("message", "email has existed!");
            }
        } else {
//            handling save
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authorityService.findById(role));
            user.setUserAuths(authorities);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user = userService.save(user);
            if (user == null) {
                //error
                model.addAttribute("user", user);
                model.addAttribute("result", "error");
                model.addAttribute("message", "server got error, code: 500");
            } else {
                //success
                model.addAttribute("user", new User());
                model.addAttribute("result", "success");
                model.addAttribute("message", "add successfully!");
            }
        }
        return "admin/user_new";
    }


    @GetMapping("user/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user.getId() == null) {
            return "home/404";
        } else {
            model.addAttribute("title", "Edit User");
            model.addAttribute("type", "edit");
            model.addAttribute("user", user);
        }
        return "admin/user_edit";
    }

    @PostMapping("user/edit/{id}/{role}")
    public String editUser(@PathVariable("id") Long id, @PathVariable("role") Long role, @ModelAttribute("user") User user, Model model) {
        if (user.getId() != id) {
            return "home/404";
        } else {
            User checkUser = userService.findByEmail(user.getEmail());


            if (checkUser != null && checkUser.getId() != id) {
                model.addAttribute("user", user);
                model.addAttribute("result", "error");
                if (checkUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                    model.addAttribute("message", "Username has existed!");
                } else if (checkUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                    model.addAttribute("message", "Email has existed!");
                }
            } else {
                User original = userService.findById(id);

                Set<Authority> roles = new HashSet<>();
                roles.add(authorityService.findById(role));
                user.setUserAuths(roles);
                model.addAttribute("user", user);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUserCart(original.getUserCart());

                user = userService.save(user);
                if (user == null) {
                    //error
                    model.addAttribute("result", "error");
                    model.addAttribute("message", "Sever got error, code: 500!");
                } else {
                    //success
                    model.addAttribute("result", "success");
                    model.addAttribute("message", "Update user successfully!");
                }
            }
        }
        return "admin/user_edit";
    }

    @GetMapping("orders")
    @Secured("ROLE_ADMIN")
    public String adminOrders(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        model.addAttribute("myOrders", orderService.findAll(page));
        return "admin/Orders";
    }

    @GetMapping("stats")
    public String adminStats(Model model) {
        model.addAttribute("listCat", categoryService.findAll());
        return "admin/stats";
    }

    @GetMapping("categories")
    public String cagegories(Model model) {
        model.addAttribute("listCats", categoryService.findAll().stream().map(cat -> {
            cat.setCar_Products(null);
            return cat;
        }).toList());
        return "admin/category";
    }
}
