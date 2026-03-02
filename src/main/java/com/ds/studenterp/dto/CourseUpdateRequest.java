package com.ds.studenterp.dto;

import lombok.Data;

@Data
public class CourseUpdateRequest {

    private String courseName;
    private String courseCode;
    private String duration;
    private String description;
    private Long departmentId;
}
