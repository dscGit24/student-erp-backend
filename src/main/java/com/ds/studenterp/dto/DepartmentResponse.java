package com.ds.studenterp.dto;

import com.ds.studenterp.entity.Department;
import lombok.Data;

@Data
public class DepartmentResponse {

    private Long id;
    private String name;
    private String code;
    private String description;
    private boolean active;

    private Long hodId;
    private String hodName;

    public DepartmentResponse() {}

    public DepartmentResponse(Department dept) {
        this.id = dept.getId();
        this.name = dept.getName();
        this.code = dept.getCode();
        this.description = dept.getDescription();
        this.active = dept.getActive();

        if (dept.getHod() != null) {
            this.hodId = dept.getHod().getId();
            this.hodName =
                    dept.getHod().getFirstName() + " " +
                            dept.getHod().getLastName();
        }
    }
}