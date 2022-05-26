package com.controller;

import com.model.Category;
import com.model.Product;
import com.service.CategoryService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    public ProductService productService;

    @Autowired
    public CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @RequestMapping
    public String home(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page, Model model) {
        model.addAttribute("listProducts", productService.findAll(page));
        return "home/home";
    }

    @RequestMapping("category/{catName}")
    public String category(@PathVariable("catName") String catName, Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Category cat = categoryService.findByName(catName);
        Page<Product> productList = productService.findAllByCategory(cat.getId(), PageRequest.of(page, 10));


        model.addAttribute("category", cat);
        model.addAttribute("listProductCart", productList);

        return "home/category";
    }

    @RequestMapping("product/{productName}/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        Product p = productService.findById(id);
        model.addAttribute("product", p);

        model.addAttribute("nearProducts", productService.findAllByCategory(p.getProduct_Cat().getId(), PageRequest.of(0, 10)).getContent());
        return "home/productDetail";
    }

    @RequestMapping("contact")
    public String contact() {
        return "home/contact";
    }

    @ModelAttribute
    public List<Product> topSales() {
        return productService.findTopSales();
    }

    @GetMapping("search")
    public String search(@RequestParam("q") String q, @RequestParam(name = "page", required = false, defaultValue = "0")Integer page,Model model) {
        model.addAttribute("keyWord", q);
        model.addAttribute("listProducts", productService.findAllByTitleLike("%".concat(q).concat("%"), page));
        return "home/search";
    }
}
