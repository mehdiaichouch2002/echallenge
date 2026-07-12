package com.echallenge.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "test_session_id", nullable = false, unique = true)
    private TestSession testSession;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private LocalDateTime completionTime;

    @Column(length = 2000)
    private String feedback;

    @Column(nullable = false)
    private boolean passed;

    public Result() {}

    public Result(TestSession testSession, int totalQuestions, int correctAnswers) {
        this.testSession = testSession;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.score = totalQuestions == 0 ? 0 : (double) correctAnswers / totalQuestions * 100;
        this.completionTime = LocalDateTime.now();
        this.passed = score >= 50;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestSession getTestSession() { return testSession; }
    public void setTestSession(TestSession testSession) { this.testSession = testSession; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public LocalDateTime getCompletionTime() { return completionTime; }
    public void setCompletionTime(LocalDateTime completionTime) { this.completionTime = completionTime; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}
