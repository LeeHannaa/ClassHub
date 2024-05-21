package com.example.classhub.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class HomeController {
    @GetMapping("/classhub")
    public String home() {
        return "home";
    }
}
