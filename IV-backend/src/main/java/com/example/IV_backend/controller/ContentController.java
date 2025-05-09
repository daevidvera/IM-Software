package com.example.IV_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String login() {
        return "login"; // returns login.html
    }

    @GetMapping("/signup") // ← Unique path
    public String signup() {
        return "signup"; // returns signup.html
    }




}
