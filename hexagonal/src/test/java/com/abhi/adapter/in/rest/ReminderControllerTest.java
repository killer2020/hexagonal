package com.abhi.adapter.in.rest;

import com.abhi.core.domain.Reminder;
import com.abhi.core.port.in.ReminderManager;
import com.abhi.core.port.in.ReminderTrigger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReminderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReminderManager reminderManager;

    @Mock
    private ReminderTrigger reminderTrigger;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ReminderController reminderController = new ReminderController(reminderManager, reminderTrigger);
        mockMvc = MockMvcBuilders.standaloneSetup(reminderController).build();
    }

    @Test
    void reminderIsCreated() throws Exception {

        String reminderRequestJson =
                objectMapper.writeValueAsString(new CreateReminderRequest(1, "Cancel netflix subscription", LocalDate.of(2025, 1, 1)));

        mockMvc.perform(post("/reminder/create").contentType(MediaType.APPLICATION_JSON)
                .content(reminderRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Created"));

          verify(reminderManager).saveTask(new Reminder(1 , "Cancel netflix subscription", LocalDate.of(2025, 1, 1)));
    }

    @Test
    void reminderIsRetrievedById() throws Exception {

        Reminder reminder = new Reminder(2 , "Doctor appointment" , LocalDate.of(2025,4,4));
        when(reminderManager.getReminder(2)).thenReturn(Optional.of(reminder));

        mockMvc.perform(get("/reminder/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.description").value("Doctor appointment"))
                .andExpect(jsonPath("$.dueDate.[0]").value("2025"))
                .andExpect(jsonPath("$.dueDate.[1]").value("4"))
                .andExpect(jsonPath("$.dueDate.[2]").value("4"));
    }

    @Test
    void ifReminderIsNotFoundByIdThenNotFoundStatusIsReturned() throws Exception {

        when(reminderManager.getReminder(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reminder/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void allRemindersStoredAreReturned() throws Exception {

        var reminder1 = new Reminder(2 , "Doctor appointment" , LocalDate.of(2025,4,4));
        var reminder2 = new Reminder(3 , "Dinner appointment" , LocalDate.of(2025,4,5));

        when(reminderManager.getAll()).thenReturn(List.of(reminder1, reminder2));

        mockMvc.perform(get("/reminder/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[0].description").value("Doctor appointment"))
                .andExpect(jsonPath("$.[0].dueDate.[0]").value("2025"))
                .andExpect(jsonPath("$.[0].dueDate.[1]").value("4"))
                .andExpect(jsonPath("$.[0].dueDate.[2]").value("4"))

                .andExpect(jsonPath("$.[1].id").value(3))
                .andExpect(jsonPath("$.[1].description").value("Dinner appointment"))
                .andExpect(jsonPath("$.[1].dueDate.[0]").value("2025"))
                .andExpect(jsonPath("$.[1].dueDate.[1]").value("4"))
                .andExpect(jsonPath("$.[1].dueDate.[2]").value("5"));

    }

    @Test
    void remindersAreTriggeredToReminderReceivers() throws Exception {

        mockMvc.perform(post("/reminder/trigger").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Triggered"));

        verify(reminderTrigger).sendReminders();
    }
}