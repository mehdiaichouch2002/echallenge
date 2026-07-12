package com.echallenge.service;

import com.echallenge.entity.Question;

import java.util.List;

public interface QuestionService {

    Question create(Question question);

    Question findById(Long id);

    List<Question> findAll();

    List<Question> findByTheme(Long themeId);

    List<Question> findRandomByTheme(Long themeId, int limit);

    Question update(Long id, Question question);

    void delete(Long id);
}
