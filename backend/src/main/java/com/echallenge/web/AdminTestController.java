package com.echallenge.web;

import com.echallenge.entity.Test;
import com.echallenge.service.TestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tests")
public class AdminTestController {

    private final TestService testService;

    public AdminTestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<Test> findAll() {
        return testService.findAll();
    }

    @GetMapping("/{id}")
    public Test findById(@PathVariable Long id) {
        return testService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Test create(@Valid @RequestBody Test test) {
        return testService.create(test);
    }

    @PutMapping("/{id}")
    public Test update(@PathVariable Long id, @Valid @RequestBody Test test) {
        return testService.update(id, test);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testService.delete(id);
    }
}
