package com.echallenge.service;

import com.echallenge.entity.Candidate;

import java.util.List;

public interface CandidateService {

    Candidate findById(Long id);

    Candidate findByEmail(String email);

    List<Candidate> findAll();

    Candidate update(Long id, Candidate candidate);

    Candidate confirm(Long id);

    void delete(Long id);
}
