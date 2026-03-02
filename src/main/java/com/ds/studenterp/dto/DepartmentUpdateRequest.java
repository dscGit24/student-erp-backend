package com.ds.studenterp.dto;

import lombok.Data;

@Data
public class DepartmentUpdateRequest {

    private String name;
    private String code;
    private String description;
    private Long hodId;
}
