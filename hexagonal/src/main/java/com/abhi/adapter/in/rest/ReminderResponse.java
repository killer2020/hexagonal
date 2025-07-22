package com.abhi.adapter.in.rest;

import com.abhi.core.domain.Reminder;

import java.time.LocalDate;

public class ReminderResponse {

    private Integer id;
    private String description;
    private LocalDate dueDate;

    public ReminderResponse(Integer id, String description, LocalDate dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public static ReminderResponse from(Reminder reminder) {
        return new ReminderResponse(reminder.getId(), reminder.getDescription(), reminder.getDueDate());
    }
}
