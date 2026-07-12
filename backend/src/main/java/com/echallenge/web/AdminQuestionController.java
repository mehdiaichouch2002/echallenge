package com.echallenge.web;

import com.echallenge.entity.Question;
import com.echallenge.entity.QuestionType;
import com.echallenge.service.QuestionService;
import com.echallenge.service.QuestionTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {

    private final QuestionService questionService;
    private final QuestionTypeService questionTypeService;

    public AdminQuestionController(QuestionService questionService, QuestionTypeService questionTypeService) {
        this.questionService = questionService;
        this.questionTypeService = questionTypeService;
    }

    @GetMapping
    public List<Question> findAll(@RequestParam(required = false) Long themeId) {
        return themeId == null ? questionService.findAll() : questionService.findByTheme(themeId);
    }

    @GetMapping("/{id}")
    public Question findById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @GetMapping("/types")
    public List<QuestionType> questionTypes() {
        return questionTypeService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Question create(@Valid @RequestBody Question question) {
        return questionService.create(question);
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Long id, @Valid @RequestBody Question question) {
        return questionService.update(id, question);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }
}
