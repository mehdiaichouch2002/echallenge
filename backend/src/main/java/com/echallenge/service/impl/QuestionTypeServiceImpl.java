package com.echallenge.service.impl;

import com.echallenge.entity.QuestionType;
import com.echallenge.repository.QuestionTypeRepository;
import com.echallenge.service.QuestionTypeService;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionTypeServiceImpl implements QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    public QuestionTypeServiceImpl(QuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

    @Override
    public QuestionType create(QuestionType questionType) {
        return questionTypeRepository.save(questionType);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionType findById(Long id) {
        return questionTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionType not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionType> findAll() {
        return questionTypeRepository.findAll();
    }
}
