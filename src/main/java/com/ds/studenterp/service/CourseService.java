package com.ds.studenterp.service;

import com.ds.studenterp.dto.CourseRequest;
import com.ds.studenterp.dto.CourseUpdateRequest;
import com.ds.studenterp.entity.Course;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.repository.CourseRepository;
import com.ds.studenterp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    // ================= CREATE =================
    public Course createCourse(CourseRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDuration(request.getDuration());
        course.setDescription(request.getDescription());
        course.setDepartment(department);
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
    public Course updateCourse(Long id, CourseUpdateRequest request) {

        Course existing = getCourseById(id);

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existing.setDepartment(department);
        }

        existing.setCourseName(request.getCourseName());
        existing.setDescription(request.getDescription());
        existing.setDuration(request.getDuration());
        existing.setCourseCode(request.getCourseCode());

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

        List<Course> courses = courseRepository.findByActiveTrue();

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