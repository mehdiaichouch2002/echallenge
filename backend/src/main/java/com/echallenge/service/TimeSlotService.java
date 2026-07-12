package com.echallenge.service;

import com.echallenge.entity.TimeSlot;

import java.util.List;

public interface TimeSlotService {

    TimeSlot create(TimeSlot timeSlot);

    TimeSlot findById(Long id);

    List<TimeSlot> findAll();

    List<TimeSlot> findAvailable();

    TimeSlot update(Long id, TimeSlot timeSlot);

    void delete(Long id);
}
