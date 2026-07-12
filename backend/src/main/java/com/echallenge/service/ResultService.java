package com.echallenge.service;

import com.echallenge.dto.SubmitTestRequest;
import com.echallenge.entity.Result;

import java.util.List;

public interface ResultService {

    /**
     * Grades a submitted test: stores the candidate's answers, computes the
     * score, marks the session COMPLETED and persists the Result.
     */
    Result submitAndGrade(Long sessionId, SubmitTestRequest submission);

    Result findById(Long id);

    Result findByTestSession(Long testSessionId);

    List<Result> findByCandidate(Long candidateId);

    List<Result> findAll();

    void delete(Long id);
}
