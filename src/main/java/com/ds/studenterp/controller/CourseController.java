package com.ds.studenterp.controller;

import com.ds.studenterp.entity.Course;
import com.ds.studenterp.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        return courseService.saveCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course){
        return courseService.updateCourse(id, course);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggleCourseStatus(@PathVariable Long id){
        return ResponseEntity.ok(courseService.toggleCourseStatus(id));
    }
}
