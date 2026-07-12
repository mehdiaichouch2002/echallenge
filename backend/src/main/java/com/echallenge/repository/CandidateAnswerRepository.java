package com.echallenge.repository;

import com.echallenge.entity.CandidateAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateAnswerRepository extends JpaRepository<CandidateAnswer, Long> {

    List<CandidateAnswer> findByTestSessionId(Long testSessionId);

    long countByTestSessionIdAndCorrectTrue(Long testSessionId);
}
