package com.ds.studenterp.service;

import com.ds.studenterp.entity.Course;
import com.ds.studenterp.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // ================= CREATE =================
    public Course saveCourse(Course course) {

        course.setActive(true);
        course.setCreatedBy("ADMIN");
        course.setUpdatedBy("ADMIN");

        return courseRepository.save(course);
    }

    // ================= READ =================
//    public List<Course> getAllCourses() {
//        return courseRepository.findByActiveTrue();
//    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course NOT Found!.."));
    }

    // ================= UPDATE =================
    public Course updateCourse(Long id, Course updatedCourse) {

        Course existing = getCourseById(id);

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setDescription(updatedCourse.getDescription());
        existing.setDuration(updatedCourse.getDuration());
        existing.setCourseCode(updatedCourse.getCourseCode());

        existing.setUpdatedBy("ADMIN");

        return courseRepository.save(existing);
    }

    // ================= SOFT DELETE =================
    public Course toggleStatus(Long id) {

        Course course = getCourseById(id);

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

    public List<Course> getAllCourses() {

        List<Course> courses = courseRepository.findAll();

        for (Course course : courses) {
            course.setStudentCount(
                    course.getStudents() != null
                            ? course.getStudents().size()
                            : 0
            );
        }

        return courses;
    }
}