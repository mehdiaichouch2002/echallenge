package com.echallenge.repository;

import com.echallenge.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);

    Optional<Candidate> findByCode(String code);
}
