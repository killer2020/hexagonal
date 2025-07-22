package com.abhi.core.port.in;

import com.abhi.core.domain.Reminder;

import java.util.List;
import java.util.Optional;

public interface ReminderManager {
    void saveTask(Reminder reminder);
    Optional<Reminder> getReminder(Integer id);
    List<Reminder> getAll();
}
