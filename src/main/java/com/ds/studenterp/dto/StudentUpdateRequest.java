package com.ds.studenterp.dto;

import lombok.Data;

@Data
public class StudentUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String aadharNumber;
    private Long departmentId;
    private Long courseId;
    private String gender;
    private String address;
    private String photo;
}
