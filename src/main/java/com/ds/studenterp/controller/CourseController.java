package com.ds.studenterp.controller;

import com.ds.studenterp.dto.CourseRequest;
import com.ds.studenterp.dto.CourseResponse;
import com.ds.studenterp.dto.CourseUpdateRequest;
import com.ds.studenterp.entity.Course;
import com.ds.studenterp.service.CourseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ================= READ ALL =================
    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses()
                .stream().map(CourseResponse::new)
                .toList();
    }

    // ================= READ ONE =================
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseUpdateRequest request) {

        return ResponseEntity.ok(
                courseService.updateCourse(id, request)
        );
    }

    // ================= SOFT DELETE (TOGGLE) =================
    @PatchMapping("/{id}/status")
    public ResponseEntity<Course> toggleCourseStatus(@PathVariable Long id) {
        return ResponseEntity.ok(
                courseService.toggleStatus(id)
        );
    }
}