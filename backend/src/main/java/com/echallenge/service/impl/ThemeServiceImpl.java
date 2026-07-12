package com.echallenge.service.impl;

import com.echallenge.entity.Theme;
import com.echallenge.repository.ThemeRepository;
import com.echallenge.service.ThemeService;
import com.echallenge.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public Theme create(Theme theme) {
        return themeRepository.save(theme);
    }

    @Override
    @Transactional(readOnly = true)
    public Theme findById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    @Override
    public Theme update(Long id, Theme theme) {
        Theme existing = findById(id);
        existing.setName(theme.getName());
        existing.setDescription(theme.getDescription());
        return themeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        themeRepository.delete(findById(id));
    }
}
