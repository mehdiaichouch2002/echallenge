package com.echallenge.service;

import com.echallenge.entity.Theme;

import java.util.List;

public interface ThemeService {

    Theme create(Theme theme);

    Theme findById(Long id);

    List<Theme> findAll();

    Theme update(Long id, Theme theme);

    void delete(Long id);
}
