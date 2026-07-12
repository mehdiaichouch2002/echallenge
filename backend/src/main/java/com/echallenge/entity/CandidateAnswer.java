package com.echallenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_answers")
public class CandidateAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_session_id", nullable = false)
    private TestSession testSession;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption selectedOption;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    private long timeSpentSeconds;

    public CandidateAnswer() {}

    public CandidateAnswer(TestSession testSession, Question question, QuestionOption selectedOption, boolean correct) {
        this.testSession = testSession;
        this.question = question;
        this.selectedOption = selectedOption;
        this.correct = correct;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestSession getTestSession() { return testSession; }
    public void setTestSession(TestSession testSession) { this.testSession = testSession; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public QuestionOption getSelectedOption() { return selectedOption; }
    public void setSelectedOption(QuestionOption selectedOption) { this.selectedOption = selectedOption; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public long getTimeSpentSeconds() { return timeSpentSeconds; }
    public void setTimeSpentSeconds(long timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }
}
