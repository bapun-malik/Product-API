package com.amazon.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amazon.product.dto.ProductDto;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("product",new ProductDto());
        return "index";
    }
}
