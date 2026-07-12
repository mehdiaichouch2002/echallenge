package com.echallenge.service.impl;

import com.echallenge.entity.Question;
import com.echallenge.repository.QuestionRepository;
import com.echallenge.service.QuestionService;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question create(Question question) {
        if (question.getOptions() != null) {
            question.getOptions().forEach(o -> o.setQuestion(question));
        }
        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findByTheme(Long themeId) {
        return questionRepository.findByThemeId(themeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findRandomByTheme(Long themeId, int limit) {
        List<Question> questions = new ArrayList<>(questionRepository.findByThemeIdAndActiveTrue(themeId));
        Collections.shuffle(questions);
        return questions.size() > limit ? questions.subList(0, limit) : questions;
    }

    @Override
    public Question update(Long id, Question question) {
        Question existing = findById(id);
        existing.setTheme(question.getTheme());
        existing.setQuestionType(question.getQuestionType());
        existing.setQuestionText(question.getQuestionText());
        existing.setExplanation(question.getExplanation());
        existing.setPoints(question.getPoints());
        existing.setActive(question.isActive());
        existing.getOptions().clear();
        if (question.getOptions() != null) {
            question.getOptions().forEach(o -> {
                o.setQuestion(existing);
                existing.getOptions().add(o);
            });
        }
        return questionRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        questionRepository.delete(findById(id));
    }
}
