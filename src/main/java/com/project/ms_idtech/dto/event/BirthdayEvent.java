package com.project.ms_idtech.dto.event;

public record BirthdayEvent(
        String personType, //(teacher or student)
        String fullName,
        String email
) { }