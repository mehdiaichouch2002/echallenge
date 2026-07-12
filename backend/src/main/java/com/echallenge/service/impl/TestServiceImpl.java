package com.echallenge.service.impl;

import com.echallenge.entity.Question;
import com.echallenge.entity.Test;
import com.echallenge.repository.TestRepository;
import com.echallenge.service.QuestionService;
import com.echallenge.service.TestService;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionService questionService;

    public TestServiceImpl(TestRepository testRepository, QuestionService questionService) {
        this.testRepository = testRepository;
        this.questionService = questionService;
    }

    @Override
    public Test create(Test test) {
        return testRepository.save(test);
    }

    @Override
    @Transactional(readOnly = true)
    public Test findById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> findActive() {
        return testRepository.findByActiveTrue();
    }

    @Override
    public Test update(Long id, Test test) {
        Test existing = findById(id);
        existing.setName(test.getName());
        existing.setDescription(test.getDescription());
        existing.setTotalDurationMinutes(test.getTotalDurationMinutes());
        existing.setQuestionDurationSeconds(test.getQuestionDurationSeconds());
        existing.setTotalQuestions(test.getTotalQuestions());
        existing.setActive(test.isActive());
        existing.getThemeQuestionCounts().clear();
        existing.getThemeQuestionCounts().putAll(test.getThemeQuestionCounts());
        return testRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        testRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> selectQuestionsForTest(Long testId) {
        Test test = findById(testId);
        List<Question> selected = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : test.getThemeQuestionCounts().entrySet()) {
            selected.addAll(questionService.findRandomByTheme(entry.getKey(), entry.getValue()));
        }
        Collections.shuffle(selected);
        return selected;
    }
}
