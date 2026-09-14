package com.project.ms_idtech.scheduler;

import com.project.ms_idtech.dto.event.BirthdayEvent;
import com.project.ms_idtech.repo.StudentRepository;
import com.project.ms_idtech.repo.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BirthdayScheduler {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RabbitTemplate rabbitTemplate;

    // Every day at 09:00
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Baku")
    public void sendBirthdayGreetings() {
        LocalDate today = LocalDate.now();

        teacherRepository.findAll().stream()
                .filter(t -> t.getBirthDate() != null &&
                        t.getBirthDate().getMonth() == today.getMonth() &&
                        t.getBirthDate().getDayOfMonth() == today.getDayOfMonth())
                .forEach(t -> rabbitTemplate.convertAndSend(
                        "notifications.events.x",
                        "birthday.teacher",
                        new BirthdayEvent("TEACHER", t.getFirstName() + " " + t.getLastName(), t.getEmail())
                ));

        studentRepository.findAll().stream()
                .filter(s -> s.getBirthDate() != null &&
                        s.getBirthDate().getMonth() == today.getMonth() &&
                        s.getBirthDate().getDayOfMonth() == today.getDayOfMonth())
                .forEach(s -> rabbitTemplate.convertAndSend(
                        "notifications.events.x",
                        "birthday.student",
                        new BirthdayEvent("STUDENT", s.getFullName(), s.getEmail())
                ));
    }
}