package com.abhi.adapter.out.persistence;

import com.abhi.adapter.out.entity.ReminderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.abhi.core.domain.Reminder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReminderStorageJpaAdapterTest {


    @InjectMocks
    private ReminderStorageJpaAdapter reminderStorageJpaAdapter;

    @Mock
    private ReminderJpaRepository reminderJpaRepository;

    @Test
    void taskIsConvertedToTaskEntityAndSavedInRepository() {

        Reminder reminder = new Reminder(1 , "Cancel Netflix" , LocalDate.now());

        reminderStorageJpaAdapter.save(reminder);

        verify(reminderJpaRepository)
                .save(new ReminderEntity(1, "Cancel Netflix" , reminder.getDueDate()));
    }

    @Test
    void reminderByIdIsReturned() {

        assertThat(reminderJpaRepository.findById(1234)).isEmpty();

        when(reminderJpaRepository.findById(1)).thenReturn(Optional.of(new ReminderEntity(1, "cancel subscription" , LocalDate.now())));

        assertThat(reminderStorageJpaAdapter.get(1)).contains(new Reminder(1, "cancel subscription" , LocalDate.now()));
    }

    @Test
    void allRemindersStoredAsReminderEntityAreReturned() {

        assertThat(reminderStorageJpaAdapter.getAll()).isNotNull().isEmpty();

        ReminderEntity reminderEntity1 = new ReminderEntity(1, "Cancel Netflix", LocalDate.now());
        ReminderEntity reminderEntity2 = new ReminderEntity(2, "Cancel Amazon", LocalDate.now());
        ReminderEntity reminderEntity3 = new ReminderEntity(3, "Cancel veo3", LocalDate.now());

        when(reminderJpaRepository.findAll()).thenReturn(List.of(reminderEntity1, reminderEntity2, reminderEntity3));

        Reminder reminder1 = new Reminder(1, "Cancel Netflix", LocalDate.now());
        Reminder reminder2 = new Reminder(2, "Cancel Amazon", LocalDate.now());
        Reminder reminder3 = new Reminder(3, "Cancel veo3", LocalDate.now());

        assertThat(reminderStorageJpaAdapter.getAll())
                .containsExactlyInAnyOrder(reminder1, reminder2, reminder3);
    }

}