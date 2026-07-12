package com.echallenge.dto;

import java.util.List;

/** Answers submitted by a candidate at the end of a test. */
public class SubmitTestRequest {

    public static class AnswerDto {
        private Long questionId;
        private Long selectedOptionId;
        private long timeSpentSeconds;

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        public Long getSelectedOptionId() { return selectedOptionId; }
        public void setSelectedOptionId(Long selectedOptionId) { this.selectedOptionId = selectedOptionId; }
        public long getTimeSpentSeconds() { return timeSpentSeconds; }
        public void setTimeSpentSeconds(long timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }
    }

    private List<AnswerDto> answers;

    public List<AnswerDto> getAnswers() { return answers; }
    public void setAnswers(List<AnswerDto> answers) { this.answers = answers; }
}
