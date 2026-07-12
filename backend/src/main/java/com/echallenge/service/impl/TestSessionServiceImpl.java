package com.echallenge.service.impl;

import com.echallenge.entity.Candidate;
import com.echallenge.entity.Test;
import com.echallenge.entity.TestSession;
import com.echallenge.entity.TimeSlot;
import com.echallenge.repository.TestSessionRepository;
import com.echallenge.repository.TimeSlotRepository;
import com.echallenge.service.CandidateService;
import com.echallenge.service.TestService;
import com.echallenge.service.TestSessionService;
import com.echallenge.service.TimeSlotService;
import com.echallenge.service.exception.BusinessException;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TestSessionServiceImpl implements TestSessionService {

    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final TestSessionRepository testSessionRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final CandidateService candidateService;
    private final TestService testService;
    private final TimeSlotService timeSlotService;

    public TestSessionServiceImpl(TestSessionRepository testSessionRepository,
                                  TimeSlotRepository timeSlotRepository,
                                  CandidateService candidateService,
                                  TestService testService,
                                  TimeSlotService timeSlotService) {
        this.testSessionRepository = testSessionRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.candidateService = candidateService;
        this.testService = testService;
        this.timeSlotService = timeSlotService;
    }

    @Override
    public TestSession enroll(Long candidateId, Long testId, Long timeSlotId) {
        Candidate candidate = candidateService.findById(candidateId);
        Test test = testService.findById(testId);
        TimeSlot timeSlot = timeSlotService.findById(timeSlotId);

        if (timeSlot.isBooked()) {
            throw new BusinessException("This time slot is already booked");
        }
        if (testSessionRepository.existsByCandidateIdAndTestIdAndStatusNot(
                candidateId, testId, TestSession.SessionStatus.CANCELLED)) {
            throw new BusinessException("Candidate is already enrolled in this test");
        }

        timeSlot.setBooked(true);
        timeSlotRepository.save(timeSlot);

        TestSession session = new TestSession(candidate, test, timeSlot, generateSessionCode());
        return testSessionRepository.save(session);
    }

    @Override
    @Transactional(readOnly = true)
    public TestSession findById(Long id) {
        return testSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestSession not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSession> findAll() {
        return testSessionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestSession> findByCandidate(Long candidateId) {
        return testSessionRepository.findByCandidateId(candidateId);
    }

    @Override
    public TestSession start(Long sessionId) {
        TestSession session = findById(sessionId);
        if (session.getStatus() == TestSession.SessionStatus.COMPLETED) {
            throw new BusinessException("This test session is already completed");
        }
        if (session.getStatus() == TestSession.SessionStatus.CANCELLED) {
            throw new BusinessException("This test session was cancelled");
        }
        if (session.getStatus() == TestSession.SessionStatus.NOT_STARTED) {
            session.setStatus(TestSession.SessionStatus.STARTED);
            session.setStartTime(LocalDateTime.now());
        }
        return testSessionRepository.save(session);
    }

    @Override
    public TestSession cancel(Long sessionId) {
        TestSession session = findById(sessionId);
        if (session.getStatus() == TestSession.SessionStatus.COMPLETED) {
            throw new BusinessException("Cannot cancel a completed session");
        }
        session.setStatus(TestSession.SessionStatus.CANCELLED);
        TimeSlot slot = session.getTimeSlot();
        slot.setBooked(false);
        timeSlotRepository.save(slot);
        return testSessionRepository.save(session);
    }

    @Override
    public void delete(Long id) {
        testSessionRepository.delete(findById(id));
    }

    private String generateSessionCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            code.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
        }
        return code.toString();
    }
}
