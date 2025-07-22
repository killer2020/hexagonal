package com.abhi.core.port.out;

import com.abhi.core.domain.Reminder;

import java.util.List;
import java.util.Optional;

public interface ReminderStorage {

    void save(Reminder reminder);

    Optional<Reminder> get(Integer id);

    List<Reminder> getAll();
}
