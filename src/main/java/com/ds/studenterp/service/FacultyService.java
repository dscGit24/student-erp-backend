package com.ds.studenterp.service;

import com.ds.studenterp.dto.FacultyRequest;
import com.ds.studenterp.dto.FacultyUpdateRequest;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.repository.DepartmentRepository;
import com.ds.studenterp.repository.FacultyRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public FacultyService(FacultyRepository facultyRepository, DepartmentRepository departmentRepository) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
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
    public Faculty updateFaculty(Long id, FacultyUpdateRequest request) {

        Faculty faculty = getFacultyById(id);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        faculty.setFirstName(request.getFirstName());
        faculty.setLastName(request.getLastName());
        faculty.setEmail(request.getEmail());
        faculty.setDepartment(department);
        faculty.setPhone(request.getPhone());
        faculty.setExperience(request.getExperience());

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

    public Faculty createFaculty(FacultyRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Faculty faculty = new Faculty();
        faculty.setFirstName(request.getFirstName());
        faculty.setLastName(request.getLastName());
        faculty.setEmail(request.getEmail());
        faculty.setPhone(request.getPhone());
        faculty.setExperience(request.getExperience());
        faculty.setActive(request.getActive());
        faculty.setDepartment(department);
        faculty.setCreatedBy("ADMIN");
        faculty.setUpdatedBy("ADMIN");

        return facultyRepository.save(faculty);
    }
}