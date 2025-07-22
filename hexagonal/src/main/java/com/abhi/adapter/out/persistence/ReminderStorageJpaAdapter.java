package com.abhi.adapter.out.persistence;

import com.abhi.adapter.out.entity.ReminderEntity;
import com.abhi.core.domain.Reminder;
import com.abhi.core.port.out.ReminderStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReminderStorageJpaAdapter implements ReminderStorage {

    private final ReminderJpaRepository reminderJpaRepository;

    public ReminderStorageJpaAdapter(ReminderJpaRepository reminderJpaRepository) {
        this.reminderJpaRepository = reminderJpaRepository;
    }

    @Override
    public void save(Reminder reminder) {
        reminderJpaRepository.save(ReminderEntity.from(reminder));
    }

    @Override
    public Optional<Reminder> get(Integer id) {

        return reminderJpaRepository.findById(id)
                .map(this::mapToReminder);
    }

    @Override
    public List<Reminder> getAll() {
        return reminderJpaRepository.findAll().stream()
                .map(this::mapToReminder).collect(Collectors.toList());
    }

    private Reminder mapToReminder(ReminderEntity entity) {
        return new Reminder(
                entity.getId(),
                entity.getDescription(),
                entity.getDueDate()
        );
    }
}
