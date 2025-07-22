package com.abhi.core.service;

import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderManager;
import com.abhi.core.port.out.ReminderStorage;

import java.util.List;
import java.util.Optional;

public class ReminderManagerService implements ReminderManager {

    private final ReminderStorage reminderStorage;

    public ReminderManagerService(ReminderStorage reminderStorage) {
        this.reminderStorage = reminderStorage;
    }

    @Override
    public void saveTask(Reminder reminder) {
       reminderStorage.save(reminder);
    }

    public Optional<Reminder> getReminder(Integer id) {
        return reminderStorage.get(id);
    }

    @Override
    public List<Reminder> getAll() {
        return reminderStorage.getAll();
    }
}
