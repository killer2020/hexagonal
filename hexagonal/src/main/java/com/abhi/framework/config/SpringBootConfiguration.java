package com.abhi.framework.config;

import com.abhi.adapter.in.rest.ReminderController;
import com.abhi.adapter.out.persistence.ReminderJpaRepository;
import com.abhi.adapter.out.persistence.ReminderStorageJpaAdapter;
import com.abhi.adapter.out.sms.SmsReminderSenderAdapter;
import com.abhi.adapter.out.sms.SmsSender;
import com.abhi.core.port.in.ReminderManager;
import com.abhi.core.port.in.ReminderTrigger;
import com.abhi.core.port.out.ReminderSender;
import com.abhi.core.port.out.ReminderStorage;
import com.abhi.core.service.ReminderManagerService;
import com.abhi.core.service.ReminderTriggerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringBootConfiguration {

    @Bean
    public ReminderStorage reminderStorage(ReminderJpaRepository reminderJpaRepository) {
        return new ReminderStorageJpaAdapter(reminderJpaRepository);
    }

    @Bean
    public ReminderSender smsReminderSender(SmsSender smsSender) {
        return new SmsReminderSenderAdapter(smsSender);
    }

    @Bean
    public ReminderManager reminderManager(ReminderStorage reminderStorage) {
        return new ReminderManagerService(reminderStorage);
    }

    @Bean
    public ReminderTrigger reminderTrigger(ReminderStorage reminderStorage, List<ReminderSender> reminderSenders) {
        return new ReminderTriggerService(reminderStorage, reminderSenders);
    }

    @Bean
    public ReminderController reminderController(ReminderManager reminderManager, ReminderTrigger reminderTrigger) {
        return new ReminderController(reminderManager, reminderTrigger);
    }

    @Bean
    public SmsSender smsSender() {
        return new SmsSender();
    }
}
