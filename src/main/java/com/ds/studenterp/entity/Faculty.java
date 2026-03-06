package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "faculties")
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties("faculties")
    private Department department;
    private String phone;

    private Integer experience; // years
    private Boolean active = true;

    @ManyToMany(mappedBy = "faculties")
    @JsonIgnore
    private List<Course> courses;
}