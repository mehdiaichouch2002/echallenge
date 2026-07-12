package com.echallenge.service;

import com.echallenge.entity.QuestionType;

import java.util.List;

public interface QuestionTypeService {

    QuestionType create(QuestionType questionType);

    QuestionType findById(Long id);

    List<QuestionType> findAll();
}
