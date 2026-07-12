package com.echallenge.entity;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int totalDurationMinutes;

    @Column(nullable = false)
    private int questionDurationSeconds;

    @Column(nullable = false)
    private int totalQuestions;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "test_theme_questions", joinColumns = @JoinColumn(name = "test_id"))
    @MapKeyJoinColumn(name = "theme_id")
    @Column(name = "question_count")
    private Map<Long, Integer> themeQuestionCounts = new HashMap<>();

    @Column(nullable = false)
    private boolean active = true;

    public Test() {}

    public Test(String name, int totalDurationMinutes, int questionDurationSeconds, int totalQuestions) {
        this.name = name;
        this.totalDurationMinutes = totalDurationMinutes;
        this.questionDurationSeconds = questionDurationSeconds;
        this.totalQuestions = totalQuestions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getTotalDurationMinutes() { return totalDurationMinutes; }
    public void setTotalDurationMinutes(int totalDurationMinutes) { this.totalDurationMinutes = totalDurationMinutes; }
    public int getQuestionDurationSeconds() { return questionDurationSeconds; }
    public void setQuestionDurationSeconds(int questionDurationSeconds) { this.questionDurationSeconds = questionDurationSeconds; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public Map<Long, Integer> getThemeQuestionCounts() { return themeQuestionCounts; }
    public void setThemeQuestionCounts(Map<Long, Integer> themeQuestionCounts) { this.themeQuestionCounts = themeQuestionCounts; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
