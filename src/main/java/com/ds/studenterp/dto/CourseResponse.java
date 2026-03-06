package com.ds.studenterp.dto;

import com.ds.studenterp.entity.Course;
import lombok.Data;

@Data
public class CourseResponse {

    private Long id;
    private String courseName;
    private String courseCode;
    private String duration;
    private String description;
    private Boolean active;

    private Long departmentId;
    private String departmentName;

    private int studentCount;

    public CourseResponse(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
        this.courseCode = course.getCourseCode();
        this.duration = course.getDuration();
        this.description = course.getDescription();
        this.active = course.getActive();

        if (course.getDepartment() != null) {
            this.departmentId = course.getDepartment().getId();
            this.departmentName = course.getDepartment().getName();
        }

        this.studentCount =
                course.getStudents() != null
                        ? course.getStudents().size()
                        : 0;
    }

    // getters & setters
}