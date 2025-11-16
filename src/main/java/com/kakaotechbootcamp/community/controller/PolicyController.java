package com.kakaotechbootcamp.community.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyController {

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    @GetMapping("/terms")
    public String terms(Model model) {
        model.addAttribute("frontendBaseUrl", frontendBaseUrl);
        return "terms";
    }

    @GetMapping("/privacy")
    public String privacy(Model model) {
        model.addAttribute("frontendBaseUrl", frontendBaseUrl);
        return "privacy";
    }
}

