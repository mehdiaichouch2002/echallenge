package com.echallenge.dto;

import java.util.List;

/** Exam payload sent to a candidate when a test session starts (no correct-answer flags). */
public class ExamDto {

    public static class OptionDto {
        private Long id;
        private String optionText;

        public OptionDto(Long id, String optionText) {
            this.id = id;
            this.optionText = optionText;
        }

        public Long getId() { return id; }
        public String getOptionText() { return optionText; }
    }

    public static class QuestionDto {
        private Long id;
        private String questionText;
        private Integer points;
        private String themeName;
        private List<OptionDto> options;

        public QuestionDto(Long id, String questionText, Integer points, String themeName, List<OptionDto> options) {
            this.id = id;
            this.questionText = questionText;
            this.points = points;
            this.themeName = themeName;
            this.options = options;
        }

        public Long getId() { return id; }
        public String getQuestionText() { return questionText; }
        public Integer getPoints() { return points; }
        public String getThemeName() { return themeName; }
        public List<OptionDto> getOptions() { return options; }
    }

    private Long sessionId;
    private String testName;
    private int totalDurationMinutes;
    private int questionDurationSeconds;
    private List<QuestionDto> questions;

    public ExamDto(Long sessionId, String testName, int totalDurationMinutes,
                   int questionDurationSeconds, List<QuestionDto> questions) {
        this.sessionId = sessionId;
        this.testName = testName;
        this.totalDurationMinutes = totalDurationMinutes;
        this.questionDurationSeconds = questionDurationSeconds;
        this.questions = questions;
    }

    public Long getSessionId() { return sessionId; }
    public String getTestName() { return testName; }
    public int getTotalDurationMinutes() { return totalDurationMinutes; }
    public int getQuestionDurationSeconds() { return questionDurationSeconds; }
    public List<QuestionDto> getQuestions() { return questions; }
}
