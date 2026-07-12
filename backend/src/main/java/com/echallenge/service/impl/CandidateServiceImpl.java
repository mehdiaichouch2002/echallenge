package com.echallenge.service.impl;

import com.echallenge.entity.Candidate;
import com.echallenge.repository.CandidateRepository;
import com.echallenge.service.CandidateService;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate findById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate findByEmail(String email) {
        return candidateRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate update(Long id, Candidate candidate) {
        Candidate existing = findById(id);
        existing.setFirstName(candidate.getFirstName());
        existing.setLastName(candidate.getLastName());
        existing.setSchool(candidate.getSchool());
        existing.setField(candidate.getField());
        existing.setGsm(candidate.getGsm());
        return candidateRepository.save(existing);
    }

    @Override
    public Candidate confirm(Long id) {
        Candidate existing = findById(id);
        existing.setConfirmed(true);
        return candidateRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        candidateRepository.delete(findById(id));
    }
}
