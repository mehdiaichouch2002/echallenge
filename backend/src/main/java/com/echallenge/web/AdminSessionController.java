package com.echallenge.web;

import com.echallenge.entity.Result;
import com.echallenge.entity.TestSession;
import com.echallenge.service.ResultService;
import com.echallenge.service.TestSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminSessionController {

    private final TestSessionService testSessionService;
    private final ResultService resultService;

    public AdminSessionController(TestSessionService testSessionService, ResultService resultService) {
        this.testSessionService = testSessionService;
        this.resultService = resultService;
    }

    @GetMapping("/sessions")
    public List<TestSession> allSessions() {
        return testSessionService.findAll();
    }

    @PutMapping("/sessions/{id}/cancel")
    public TestSession cancelSession(@PathVariable Long id) {
        return testSessionService.cancel(id);
    }

    @DeleteMapping("/sessions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable Long id) {
        testSessionService.delete(id);
    }

    @GetMapping("/results")
    public List<Result> allResults() {
        return resultService.findAll();
    }
}
