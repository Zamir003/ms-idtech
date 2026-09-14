package com.project.ms_idtech.controller;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.course.CourseCreateRequest;
import com.project.ms_idtech.dto.course.CoursePatchRequest;
import com.project.ms_idtech.dto.course.CourseResponse;
import com.project.ms_idtech.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CourseCreateRequest req) {
        return courseService.create(req);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CourseResponse> list(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return courseService.list(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse get(@PathVariable Long id) {
        return courseService.get(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse patch(@PathVariable Long id, @RequestBody CoursePatchRequest req) {
        return courseService.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}