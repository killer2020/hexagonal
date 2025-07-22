package com.abhi.core.service;

import com.abhi.core.adapter.out.InMemoryReminderStorage;
import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReminderManagerServiceTest {

    private ReminderManager reminderManager;

    @BeforeEach
    void setUp() {
      reminderManager = new ReminderManagerService(new InMemoryReminderStorage());
    }

    @Test
    void taskSubmittedIsStoredAndRetrieved() {

        Reminder reminder = new Reminder(1234, "Cancel netflix subscription" , LocalDate.now().plusDays(2));
        reminderManager.saveTask(reminder);
        Optional<Reminder> fetchedTask = reminderManager.getReminder(1234);

        assertThat(fetchedTask).contains(reminder);
    }

    @Test
    void emptyOptionalReturnedIfTaskDoesNotExist() {
        assertThat(reminderManager.getReminder(5678)).isEmpty();
    }

    @Test
    void allTasksStoredAreReturned() {

        assertThat(reminderManager.getAll()).isNotNull().isEmpty();

        Reminder reminder1 = new Reminder(1, "Cancel netflix subscription" , LocalDate.now().plusDays(2));
        Reminder reminder2 = new Reminder(2, "Cancel apple music subscription" , LocalDate.now());
        Reminder reminder3 = new Reminder(3, "Cancel amazon prime subscription" , LocalDate.now().minusDays(2));

        reminderManager.saveTask(reminder1);
        reminderManager.saveTask(reminder2);
        reminderManager.saveTask(reminder3);

        List<Reminder> reminders = reminderManager.getAll();

        assertThat(reminders).containsExactlyInAnyOrder(reminder1, reminder2, reminder3);
    }

}