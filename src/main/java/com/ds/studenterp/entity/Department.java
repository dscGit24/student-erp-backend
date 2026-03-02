package com.ds.studenterp.entity;

import com.ds.studenterp.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code; // CSE, IT, MECH

    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    // HOD
    @ManyToOne
    @JoinColumn(name = "hod_id", nullable = true)
    private Faculty hod;

    // Relationships
    @OneToMany(mappedBy = "department")
    private List<Student> students;

    @OneToMany(mappedBy = "department")
    @JsonIgnoreProperties("department")
    private List<Faculty> faculties;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;
}