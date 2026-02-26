package com.ds.studenterp.service;

import com.ds.studenterp.entity.Course;
import com.ds.studenterp.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course){
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse){
        Course existing = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course NOT Found!.."));

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setDescription(updatedCourse.getDescription());
        existing.setDuration(updatedCourse.getDuration());
        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setActive(updatedCourse.getActive());

        return courseRepository.save(existing);
    }

    public Course toggleStatus(Long id) {

        Course course = courseRepository.findById(id).orElseThrow();

        if (course.getActive()) {
            course.setActive(false);
            course.setDeletedAt(LocalDateTime.now());
            course.setDeletedBy("ADMIN");
        } else {
            course.setActive(true);
            course.setDeletedAt(null);
            course.setDeletedBy(null);
        }

        course.setUpdatedBy("ADMIN");

        return courseRepository.save(course);
    }
}
