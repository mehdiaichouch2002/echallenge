package com.echallenge.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "question_type_id", nullable = false)
    private QuestionType questionType;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Column(length = 2000)
    private String explanation;

    @Column(nullable = false)
    private Integer points = 1;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("displayOrder ASC")
    private List<QuestionOption> options = new ArrayList<>();

    public Question() {}

    public Question(Theme theme, QuestionType questionType, String questionText) {
        this.theme = theme;
        this.questionType = questionType;
        this.questionText = questionText;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Theme getTheme() { return theme; }
    public void setTheme(Theme theme) { this.theme = theme; }
    public QuestionType getQuestionType() { return questionType; }
    public void setQuestionType(QuestionType questionType) { this.questionType = questionType; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<QuestionOption> getOptions() { return options; }
    public void setOptions(List<QuestionOption> options) { this.options = options; }
}
