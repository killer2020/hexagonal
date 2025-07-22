package com.abhi.adapter.out.entity;

import com.abhi.core.domain.Reminder;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class ReminderEntity {

    @Id
    private Integer id;
    private String description;
    private LocalDate dueDate;

    public ReminderEntity(){}

    public ReminderEntity(Integer id, String description, LocalDate dueDate) {
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
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReminderEntity that = (ReminderEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate);
    }

    public static ReminderEntity from(Reminder reminder) {
        return new ReminderEntity(reminder.getId(), reminder.getDescription(), reminder.getDueDate());
    }
}
