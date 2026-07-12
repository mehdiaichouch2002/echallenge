package com.echallenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "question_options")
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 1000)
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private boolean correct = false;

    private int displayOrder;

    public QuestionOption() {}

    public QuestionOption(Question question, String optionText, boolean correct, int displayOrder) {
        this.question = question;
        this.optionText = optionText;
        this.correct = correct;
        this.displayOrder = displayOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
