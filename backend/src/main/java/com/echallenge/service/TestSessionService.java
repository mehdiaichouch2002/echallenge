package com.echallenge.service;

import com.echallenge.entity.TestSession;

import java.util.List;

public interface TestSessionService {

    /** Enrolls a candidate in a test on a given time slot (books the slot). */
    TestSession enroll(Long candidateId, Long testId, Long timeSlotId);

    TestSession findById(Long id);

    List<TestSession> findAll();

    List<TestSession> findByCandidate(Long candidateId);

    /** Marks the session as started. */
    TestSession start(Long sessionId);

    TestSession cancel(Long sessionId);

    void delete(Long id);
}
