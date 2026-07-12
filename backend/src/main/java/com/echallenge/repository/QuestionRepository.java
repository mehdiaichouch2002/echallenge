package com.echallenge.repository;

import com.echallenge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByThemeId(Long themeId);

    List<Question> findByThemeIdAndActiveTrue(Long themeId);

    long countByThemeIdAndActiveTrue(Long themeId);
}
