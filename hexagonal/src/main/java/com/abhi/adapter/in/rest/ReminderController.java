package com.abhi.adapter.in.rest;

import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderManager;
import com.abhi.core.port.in.ReminderTrigger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

    private final ReminderManager reminderManager;
    private final ReminderTrigger reminderTrigger;

    public ReminderController(ReminderManager reminderManager, ReminderTrigger reminderTrigger) {
        this.reminderManager = reminderManager;
        this.reminderTrigger = reminderTrigger;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateReminderRequest createReminderRequest) {

        var reminder = new Reminder(createReminderRequest.getId(), createReminderRequest.getDescription(), createReminderRequest.getDueDate());

        reminderManager.saveTask(reminder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getReminder(@PathVariable Integer id) {
        Optional<Reminder> reminder = reminderManager.getReminder(id);

        return  reminder.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(ReminderResponse.from(reminder.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReminderResponse>> getAll() {

        var reminderResponses = reminderManager.getAll().stream().map(ReminderResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(reminderResponses);
    }

    @PostMapping("/trigger")
    public ResponseEntity<String> triggerReminders() {
        reminderTrigger.sendReminders();
        return ResponseEntity.status(HttpStatus.OK).body("Triggered");
    }
}
