package com.abhi.core.adapter.out;

import com.abhi.core.domain.Reminder;
import com.abhi.core.port.out.ReminderStorage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryReminderStorage implements ReminderStorage {

    private Map<Integer , Reminder> cache = new ConcurrentHashMap<>();

    @Override
    public void save(Reminder reminder) {
        cache.put(reminder.getId(), reminder);
    }

    @Override
    public Optional<Reminder> get(Integer id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public List<Reminder> getAll() {
        return cache.values().stream().toList();
    }
}
