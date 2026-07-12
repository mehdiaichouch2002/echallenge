package com.echallenge.web;

import com.echallenge.entity.Theme;
import com.echallenge.service.ThemeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/themes")
public class AdminThemeController {

    private final ThemeService themeService;

    public AdminThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public List<Theme> findAll() {
        return themeService.findAll();
    }

    @GetMapping("/{id}")
    public Theme findById(@PathVariable Long id) {
        return themeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Theme create(@Valid @RequestBody Theme theme) {
        return themeService.create(theme);
    }

    @PutMapping("/{id}")
    public Theme update(@PathVariable Long id, @Valid @RequestBody Theme theme) {
        return themeService.update(id, theme);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        themeService.delete(id);
    }
}
