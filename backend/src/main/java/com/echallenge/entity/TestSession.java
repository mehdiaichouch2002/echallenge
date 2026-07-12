package com.echallenge.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_sessions")
public class TestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "timeslot_id", nullable = false)
    private TimeSlot timeSlot;

    @Column(nullable = false, unique = true)
    private String sessionCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public enum SessionStatus {
        NOT_STARTED,
        STARTED,
        COMPLETED,
        EXPIRED,
        CANCELLED
    }

    public TestSession() {}

    public TestSession(Candidate candidate, Test test, TimeSlot timeSlot, String sessionCode) {
        this.candidate = candidate;
        this.test = test;
        this.timeSlot = timeSlot;
        this.sessionCode = sessionCode;
        this.status = SessionStatus.NOT_STARTED;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }
    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlot timeSlot) { this.timeSlot = timeSlot; }
    public String getSessionCode() { return sessionCode; }
    public void setSessionCode(String sessionCode) { this.sessionCode = sessionCode; }
    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus status) { this.status = status; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}
