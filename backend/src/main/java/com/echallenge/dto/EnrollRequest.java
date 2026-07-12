package com.echallenge.dto;

import jakarta.validation.constraints.NotNull;

public class EnrollRequest {

    @NotNull
    private Long testId;

    @NotNull
    private Long timeSlotId;

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }
    public Long getTimeSlotId() { return timeSlotId; }
    public void setTimeSlotId(Long timeSlotId) { this.timeSlotId = timeSlotId; }
}
