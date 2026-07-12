package com.echallenge.web;

import com.echallenge.entity.Test;
import com.echallenge.entity.Theme;
import com.echallenge.service.TestService;
import com.echallenge.service.ThemeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Public read-only catalog: available tests and themes (no authentication required). */
@RestController
@RequestMapping("/api")
public class PublicCatalogController {

    private final TestService testService;
    private final ThemeService themeService;

    public PublicCatalogController(TestService testService, ThemeService themeService) {
        this.testService = testService;
        this.themeService = themeService;
    }

    @GetMapping("/tests")
    public List<Test> activeTests() {
        return testService.findActive();
    }

    @GetMapping("/tests/{id}")
    public Test test(@PathVariable Long id) {
        return testService.findById(id);
    }

    @GetMapping("/themes")
    public List<Theme> themes() {
        return themeService.findAll();
    }
}
