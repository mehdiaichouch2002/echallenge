package com.echallenge.repository;

import com.echallenge.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByTestSessionId(Long testSessionId);

    List<Result> findByTestSessionCandidateId(Long candidateId);
}
