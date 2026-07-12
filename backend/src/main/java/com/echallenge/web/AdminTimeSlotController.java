package com.echallenge.web;

import com.echallenge.entity.TimeSlot;
import com.echallenge.service.TimeSlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/timeslots")
public class AdminTimeSlotController {

    private final TimeSlotService timeSlotService;

    public AdminTimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public List<TimeSlot> findAll() {
        return timeSlotService.findAll();
    }

    @GetMapping("/{id}")
    public TimeSlot findById(@PathVariable Long id) {
        return timeSlotService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeSlot create(@Valid @RequestBody TimeSlot timeSlot) {
        return timeSlotService.create(timeSlot);
    }

    @PutMapping("/{id}")
    public TimeSlot update(@PathVariable Long id, @Valid @RequestBody TimeSlot timeSlot) {
        return timeSlotService.update(id, timeSlot);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        timeSlotService.delete(id);
    }
}
