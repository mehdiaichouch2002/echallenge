package com.echallenge.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/** Status page shown when opening the backend URL directly in a browser. */
@RestController
public class ApiRootController {

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("application", "EChallenge — Plateforme d'examens en ligne (API)");
        info.put("status", "opérationnelle ✓");
        info.put("interface", "http://localhost:5173");
        info.put("endpoints", Map.of(
                "authentification", "POST /api/auth/login · POST /api/auth/register",
                "catalogue public", "GET /api/tests · GET /api/themes",
                "administration (rôle ADMIN)", "/api/admin/**",
                "espace candidat (rôle CANDIDATE)", "/api/candidate/**"));
        return info;
    }
}
