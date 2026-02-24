package com.ds.studenterp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//  ================= BASIC INFORMATION ===============

    @NotBlank(message = "First Name Required!..")
    private String firstName;

    @NotBlank(message = "Last Name Required!..")
    private String lastName;

    @NotBlank(message = "Email Required!..")
    @Email(message = "Invalid Email Format!..")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Contact Required!..")
    @Pattern(regexp = "\\d{10}", message = "Contact must have 10 DIGITS!..")
    @Column(unique = true)
    private String phone;

//  ================= IDENTITY ===============

    @NotBlank(message = "Aadhar Number Required!..")
    @Size(min = 12, max = 12, message = "Aadhar must be 12 digits!..")
    @Pattern(regexp = "\\d{12}", message = "Aadhar must have 12 DIGITS!..")
    @Column(unique = true)
    private String aadharNumber;

    @Column(unique = true)
    private String enrollmentNumber; // auto-generated

//  ================= ACADEMIC ===============

    @NotBlank(message = "Department Required!..")
    private String department;

    @NotBlank(message = "Course Required!..")
    private String course;

    private LocalDateTime admissionDate;

//  ================= PERSONAL ===============

    @NotNull(message = "Date of Birth Required!..")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender Required!..")
    private String gender;

    @NotBlank(message = "Address Required!..")
    private String address;

//  ================= PHOTO (BLOB) ===============

    @Column(columnDefinition = "TEXT")
    private String photo;

//  ================= AUDIT FIELDS ===============

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean deleted = false;

//  ================= AUTO TIMESTAMPS ===============

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        admissionDate = LocalDateTime.now();
        if (deleted == null){
            deleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
