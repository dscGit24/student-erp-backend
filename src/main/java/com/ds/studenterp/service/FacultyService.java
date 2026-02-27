package com.ds.studenterp.service;

import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.repository.FacultyRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // ================= CREATE =================
    public Faculty addFaculty(Faculty faculty) {

        faculty.setActive(true);
        faculty.setCreatedBy("ADMIN");
        faculty.setUpdatedBy("ADMIN");

        return facultyRepository.save(faculty);
    }

    // ================= READ =================
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findByActiveTrue();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty Not Found!.."));
    }

    // ================= UPDATE =================
    public Faculty updateFaculty(Long id, Faculty updated) {

        Faculty faculty = getFacultyById(id);

        faculty.setFirstName(updated.getFirstName());
        faculty.setLastName(updated.getLastName());
        faculty.setEmail(updated.getEmail());
        faculty.setDepartment(updated.getDepartment());
        faculty.setPhone(updated.getPhone());

        faculty.setUpdatedBy("ADMIN");

        return facultyRepository.save(faculty);
    }

    // ================= SOFT DELETE =================
    public Faculty toggleStatus(Long id) {

        Faculty faculty = getFacultyById(id);

        if (faculty.getActive()) {
            faculty.setActive(false);
            faculty.setDeletedAt(LocalDateTime.now());
            faculty.setDeletedBy("ADMIN");
        } else {
            faculty.setActive(true);
            faculty.setDeletedAt(null);
            faculty.setDeletedBy(null);
        }

        faculty.setUpdatedBy("ADMIN");

        return facultyRepository.save(faculty);
    }
}