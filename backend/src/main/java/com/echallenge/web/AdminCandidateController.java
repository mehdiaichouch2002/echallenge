package com.echallenge.web;

import com.echallenge.entity.Candidate;
import com.echallenge.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/candidates")
public class AdminCandidateController {

    private final CandidateService candidateService;

    public AdminCandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public List<Candidate> findAll() {
        return candidateService.findAll();
    }

    @GetMapping("/{id}")
    public Candidate findById(@PathVariable Long id) {
        return candidateService.findById(id);
    }

    @PutMapping("/{id}")
    public Candidate update(@PathVariable Long id, @RequestBody Candidate candidate) {
        return candidateService.update(id, candidate);
    }

    @PutMapping("/{id}/confirm")
    public Candidate confirm(@PathVariable Long id) {
        return candidateService.confirm(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        candidateService.delete(id);
    }
}
