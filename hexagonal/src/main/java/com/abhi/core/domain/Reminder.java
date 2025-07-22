package com.abhi.core.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reminder {

    private Integer id;
    private String description;
    private LocalDate dueDate;

    public Reminder(Integer id, String description, LocalDate dueDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(id, reminder.id) && Objects.equals(description, reminder.description) && Objects.equals(dueDate, reminder.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
