package com.echallenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.echallenge.entity.TestSession;

public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

    List<TestSession> findByCandidateId(Long candidateId);

    Optional<TestSession> findBySessionCode(String sessionCode);

    boolean existsByCandidateIdAndTestIdAndStatusNot(Long candidateId, Long testId, TestSession.SessionStatus status);

    boolean existsByTimeSlotId(Long timeSlotId);
}
