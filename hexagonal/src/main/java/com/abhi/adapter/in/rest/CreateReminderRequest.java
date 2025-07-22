package com.abhi.adapter.in.rest;

import java.time.LocalDate;

public class CreateReminderRequest {

    private Integer id;
    private String description;
    private LocalDate dueDate;

    public CreateReminderRequest(Integer id, String description, LocalDate dueDate) {
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
}
