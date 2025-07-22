package com.abhi.core.service;

import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderTrigger;
import com.abhi.core.port.out.ReminderSender;
import com.abhi.core.port.out.ReminderStorage;

import java.time.LocalDate;
import java.util.List;

public class ReminderTriggerService implements ReminderTrigger {

    private final ReminderStorage reminderStorage;

    private final List<ReminderSender> reminderSenders;

    public ReminderTriggerService(ReminderStorage reminderStorage, List<ReminderSender> reminderSenders) {
        this.reminderStorage = reminderStorage;
        this.reminderSenders = reminderSenders;
    }

    @Override
    public void sendReminders() {

        List<Reminder> tasksDueToday = reminderStorage.getAll().stream()
                .filter(this::isDueToday)
                .toList();

        reminderSenders.forEach(reminderSender -> sendReminders(reminderSender, tasksDueToday));
    }

    private void sendReminders(ReminderSender reminderSender, List<Reminder> tasksDueToday) {
        tasksDueToday.forEach(task -> reminderSender.sendReminder(task.getDescription(), task.getDueDate()));
    }

    private boolean isDueToday(Reminder reminder) {
        return reminder.getDueDate().equals(LocalDate.now());
    }
}
