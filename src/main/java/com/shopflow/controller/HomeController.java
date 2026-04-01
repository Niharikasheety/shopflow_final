package com.shopflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
public class HomeController {

    /**
     * Serves the frontend SPA at root URL.
     * Spring Boot automatically serves /static/index.html when we forward here.
     */
    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    /**
     * Health check endpoint — returns JSON.
     * Used by Kubernetes readinessProbe and Render health checks.
     */
    @GetMapping("/health")
    @ResponseBody
    public Map<String, String> health() {
        return Map.of("status", "UP", "app", "ShopFlow", "version", "1.0.0");
    }
}
