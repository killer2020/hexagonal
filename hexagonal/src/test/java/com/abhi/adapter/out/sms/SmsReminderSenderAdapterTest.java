package com.abhi.adapter.out.sms;

import com.abhi.core.domain.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class SmsReminderSenderAdapterTest {

    @InjectMocks
    private SmsReminderSenderAdapter smsReminderSenderAdapter;

    @Mock
    private SmsSender smsSender;

    @Test
    void smsIsCreatedFromReminderAndSent() {

        smsReminderSenderAdapter.sendReminder("Cancel netflix subscription" , LocalDate.now());

        verify(smsSender)
                .send(new Sms("Cancel netflix subscription is due on " + LocalDate.now() , "Reminder app reminders"));
    }
}