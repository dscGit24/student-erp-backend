package com.ds.studenterp.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String aadharNumber;
    private Long departmentId;
    private Long courseId;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String photo;
    private Boolean active;
}