package com.ds.studenterp.dto;

import lombok.Data;

@Data
public class FacultyRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long departmentId;
    private Integer experience;
    private Boolean active;
}