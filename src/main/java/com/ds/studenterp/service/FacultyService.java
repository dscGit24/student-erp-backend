package com.ds.studenterp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.repository.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty updated) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow();

        faculty.setFirstName(updated.getFirstName());
        faculty.setLastName(updated.getLastName());
        faculty.setEmail(updated.getEmail());
        faculty.setDepartment(updated.getDepartment());
        faculty.setPhone(updated.getPhone());

        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty toggleStatus(Long id) {

        Faculty faculty = facultyRepository.findById(id).orElseThrow();

        if (faculty.getActive()) {
            // SOFT DELETE
            faculty.setActive(false);
            faculty.setDeletedAt(LocalDateTime.now());
            faculty.setDeletedBy("ADMIN");
        } else {
            // RESTORE
            faculty.setActive(true);
            faculty.setDeletedAt(null);
            faculty.setDeletedBy(null);
        }

        faculty.setUpdatedBy("ADMIN");

        return facultyRepository.save(faculty);
    }
}