package com.echallenge.service;

import com.echallenge.entity.Question;
import com.echallenge.entity.Test;

import java.util.List;

public interface TestService {

    Test create(Test test);

    Test findById(Long id);

    List<Test> findAll();

    List<Test> findActive();

    Test update(Long id, Test test);

    void delete(Long id);

    /**
     * Selects the questions for a test run: for each theme configured on the
     * test, picks the configured number of random active questions.
     */
    List<Question> selectQuestionsForTest(Long testId);
}
