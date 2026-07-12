package com.echallenge.repository;

import com.echallenge.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByBookedFalseOrderByStartTimeAsc();
}
