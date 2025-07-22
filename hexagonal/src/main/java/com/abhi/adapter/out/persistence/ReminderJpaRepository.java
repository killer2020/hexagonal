package com.abhi.adapter.out.persistence;

import com.abhi.adapter.out.entity.ReminderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderJpaRepository extends JpaRepository<ReminderEntity, Integer> {
}
