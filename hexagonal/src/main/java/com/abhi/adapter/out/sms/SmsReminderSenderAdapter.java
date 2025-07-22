package com.abhi.adapter.out.sms;

import com.abhi.core.port.out.ReminderSender;

import java.time.LocalDate;

public class SmsReminderSenderAdapter implements ReminderSender {

    private final SmsSender smsSender;

    public SmsReminderSenderAdapter(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    @Override
    public void sendReminder(String description, LocalDate time) {
        smsSender.send(new Sms(description + " is due on " + time , "Reminder app reminders"));
    }
}
