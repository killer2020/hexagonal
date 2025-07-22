package com.abhi.core.port.out;

import java.time.LocalDate;

public interface ReminderSender {
    void sendReminder(String description, LocalDate time);
}
