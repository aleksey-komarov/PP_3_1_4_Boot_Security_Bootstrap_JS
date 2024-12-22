package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/admin")
    public String showAllUser() {
        return "admin";
    }

    @GetMapping("/user")
    public String showOneUser() {
        return "user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}

