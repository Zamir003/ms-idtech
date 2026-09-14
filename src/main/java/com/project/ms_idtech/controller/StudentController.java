package com.project.ms_idtech.controller;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.student.StudentCreateRequest;
import com.project.ms_idtech.dto.student.StudentResponse;
import com.project.ms_idtech.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@Valid @RequestBody StudentCreateRequest req) {
        return studentService.create(req);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<StudentResponse> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return studentService.list(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse get(@PathVariable Long id) {
        return studentService.get(id);
    }
}