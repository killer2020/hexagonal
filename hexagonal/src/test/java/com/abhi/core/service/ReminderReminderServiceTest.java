package com.abhi.core.service;

import com.abhi.core.adapter.out.InMemoryReminderStorage;
import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderTrigger;
import com.abhi.core.port.out.ReminderSender;
import com.abhi.core.port.out.ReminderStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReminderReminderServiceTest {

    private ReminderTrigger reminderTrigger;
    private ReminderStorage reminderStorage;

    @Mock
    private ReminderSender emailReceiver;

    @Mock
    ReminderSender smsReceiver;

    @BeforeEach
    void setUp() {
        reminderStorage = new InMemoryReminderStorage();
        reminderTrigger = new ReminderTriggerService(reminderStorage, List.of(emailReceiver, smsReceiver));
    }

    @Test
    void remindersForTasksWhichAreDueTodayAreSent() {

        LocalDate now = LocalDate.now();
        reminderStorage.save(new Reminder(1, "Cancel netflix subscription" , now));
        reminderStorage.save(new Reminder(2, "Cancel apple music subscription" , now));
        reminderStorage.save(new Reminder(3, "Cancel amazon subscription" , now.plusDays(1)));
        reminderStorage.save(new Reminder(4, "Cancel google veo subscription" , now.minusDays(1)));

        reminderTrigger.sendReminders();

        verify(emailReceiver, times(2)).sendReminder(any(), any());
        verify(emailReceiver).sendReminder("Cancel netflix subscription", now);
        verify(emailReceiver).sendReminder("Cancel apple music subscription", now);

        verify(smsReceiver, times(2)).sendReminder(any(), any());
        verify(smsReceiver).sendReminder("Cancel netflix subscription", now);
        verify(smsReceiver).sendReminder("Cancel apple music subscription", now);
    }
}