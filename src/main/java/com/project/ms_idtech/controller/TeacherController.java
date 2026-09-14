package com.project.ms_idtech.controller;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.teacher.TeacherCreateRequest;
import com.project.ms_idtech.dto.teacher.TeacherPatchRequest;
import com.project.ms_idtech.dto.teacher.TeacherResponse;
import com.project.ms_idtech.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse create(@Valid @RequestBody TeacherCreateRequest req) {
        return teacherService.create(req);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<TeacherResponse> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        return teacherService.list(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponse get(@PathVariable Long id) {
        return teacherService.get(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponse patch(@PathVariable Long id, @RequestBody TeacherPatchRequest req) {
        return teacherService.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}