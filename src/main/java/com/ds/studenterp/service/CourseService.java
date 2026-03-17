package com.ds.studenterp.service;

import com.ds.studenterp.dto.CourseRequest;
import com.ds.studenterp.dto.CourseUpdateRequest;
import com.ds.studenterp.entity.Course;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.repository.CourseRepository;
import com.ds.studenterp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Course createCourse(CourseRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + request.getDepartmentId()));

        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDuration(request.getDuration());
        course.setDescription(request.getDescription());
        course.setDepartment(department);
        course.setActive(true); // New courses are active by default
        course.setCreatedBy("ADMIN");
        course.setUpdatedBy("ADMIN");

        return courseRepository.save(course);
    }

    // ================= READ =================
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course NOT Found with id: " + id));
    }

    // ================= UPDATE =================
    @Transactional
    public Course updateCourse(Long id, CourseUpdateRequest request) {

        Course existing = getCourseById(id);

        // Update department if provided
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + request.getDepartmentId()));
            existing.setDepartment(department);
        }

        // Update basic fields
        if (request.getCourseName() != null) {
            existing.setCourseName(request.getCourseName());
        }

        if (request.getCourseCode() != null) {
            existing.setCourseCode(request.getCourseCode());
        }

        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        if (request.getDuration() != null) {
            existing.setDuration(request.getDuration());
        }

        // 🔥 FIX: Update active status if provided
        if (request.getActive() != null) {
            existing.setActive(request.getActive());
        }

        existing.setUpdatedBy("ADMIN");

        return courseRepository.save(existing);
    }

    // ================= SOFT DELETE =================
    @Transactional
    public Course toggleStatus(Long id) {

        Course course = getCourseById(id);

        if (course.getActive()) {
            // SOFT DELETE
            course.setActive(false);
            course.setDeletedAt(LocalDateTime.now());
            course.setDeletedBy("ADMIN");
        } else {
            // RESTORE
            course.setActive(true);
            course.setDeletedAt(null);
            course.setDeletedBy(null);
        }

        course.setUpdatedBy("ADMIN");

        return courseRepository.save(course);
    }

    // ================= GET ALL COURSES WITH STUDENT COUNT =================
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

    // ================= GET ACTIVE COURSES ONLY =================
    public List<Course> getActiveCourses() {
        return courseRepository.findByActiveTrue();
    }
}