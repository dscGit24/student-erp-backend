package com.ds.studenterp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    @Column(unique = true)
    private String courseCode;

    private String duration;

    private String description;

    private Boolean active = true;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Student> students;

    @Transient
    public int getStudentCount() {
        return students != null ? students.size() : 0;
    }
}
