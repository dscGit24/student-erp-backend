package com.ds.studenterp.dto;

import com.ds.studenterp.entity.Student;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentResponse {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String aadharNumber;
    private String enrollmentNumber;

    private Long departmentId;
    private String departmentName;

    private Long courseId;
    private String courseName;

    private LocalDateTime admissionDate;

    private LocalDate dateOfBirth;
    private String gender;
    private String address;

    private String photo;

    private Boolean active;

    public StudentResponse(Student s) {

        this.id = s.getId();

        this.firstName = s.getFirstName();
        this.lastName = s.getLastName();
        this.email = s.getEmail();
        this.phone = s.getPhone();

        this.aadharNumber = s.getAadharNumber();
        this.enrollmentNumber = s.getEnrollmentNumber();

        if (s.getDepartment() != null) {
            this.departmentId = s.getDepartment().getId();
            this.departmentName = s.getDepartment().getName();
        }

        if (s.getCourse() != null) {
            this.courseId = s.getCourse().getId();
            this.courseName = s.getCourse().getCourseName();
        }

        this.admissionDate = s.getAdmissionDate();
        this.dateOfBirth = s.getDateOfBirth();
        this.gender = s.getGender();
        this.address = s.getAddress();
        this.photo = s.getPhoto();

        this.active = s.getActive();
    }
}