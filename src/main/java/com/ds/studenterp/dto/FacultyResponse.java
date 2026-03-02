package com.ds.studenterp.dto;

import com.ds.studenterp.entity.Faculty;
import lombok.Data;

@Data
public class FacultyResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int experience;
    private boolean active;

    private Long departmentId;
    private String departmentName;

    public FacultyResponse() {}

    public FacultyResponse(Faculty faculty) {
        this.id = faculty.getId();
        this.firstName = faculty.getFirstName();
        this.lastName = faculty.getLastName();
        this.email = faculty.getEmail();
        this.phone = faculty.getPhone();
        this.experience = faculty.getExperience();
        this.active = faculty.getActive();

        if (faculty.getDepartment() != null) {
            this.departmentId = faculty.getDepartment().getId();
            this.departmentName = faculty.getDepartment().getName();
        }
    }
}