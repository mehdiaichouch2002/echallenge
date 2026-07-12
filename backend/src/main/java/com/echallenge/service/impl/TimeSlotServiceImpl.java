package com.echallenge.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echallenge.entity.TimeSlot;
import com.echallenge.repository.TestSessionRepository;
import com.echallenge.repository.TimeSlotRepository;
import com.echallenge.service.TimeSlotService;
import com.echallenge.service.exception.BusinessException;
import com.echallenge.service.exception.ResourceNotFoundException;

@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final TestSessionRepository testSessionRepository;

    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository,
                               TestSessionRepository testSessionRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.testSessionRepository = testSessionRepository;
    }

    @Override
    public TimeSlot create(TimeSlot timeSlot) {
        timeSlot.calculateEndTime();
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    @Transactional(readOnly = true)
    public TimeSlot findById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAll() {
        return timeSlotRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAvailable() {
        return timeSlotRepository.findByBookedFalseOrderByStartTimeAsc();
    }

    @Override
    public TimeSlot update(Long id, TimeSlot timeSlot) {
        TimeSlot existing = findById(id);
        existing.setStartTime(timeSlot.getStartTime());
        existing.setDurationMinutes(timeSlot.getDurationMinutes());
        existing.calculateEndTime();
        existing.setBooked(timeSlot.isBooked());
        return timeSlotRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        TimeSlot timeSlot = findById(id);
        if (timeSlot.isBooked()) {
            throw new BusinessException("Impossible de supprimer un créneau réservé");
        }
        if (testSessionRepository.existsByTimeSlotId(id)) {
            throw new BusinessException("Impossible de supprimer ce créneau lié à une séance de test");
        }
        timeSlotRepository.delete(timeSlot);
    }
}
