package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    private String department;
    private String phone;

    private Integer experience; // years
    private Boolean active;

    @ManyToMany(mappedBy = "faculties")
    @JsonIgnore
    private List<Course> courses;
}