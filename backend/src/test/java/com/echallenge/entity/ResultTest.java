package com.echallenge.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void scoreIsPercentageOfCorrectAnswers() {
        Result result = new Result(new TestSession(), 4, 3);

        assertEquals(75.0, result.getScore());
        assertTrue(result.isPassed());
    }

    @Test
    void failsBelowFiftyPercent() {
        Result result = new Result(new TestSession(), 4, 1);

        assertEquals(25.0, result.getScore());
        assertFalse(result.isPassed());
    }

    @Test
    void zeroQuestionsDoesNotDivideByZero() {
        Result result = new Result(new TestSession(), 0, 0);

        assertEquals(0.0, result.getScore());
        assertFalse(result.isPassed());
    }
}
