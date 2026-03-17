package com.ds.studenterp.service;

import com.ds.studenterp.dto.DepartmentRequest;
import com.ds.studenterp.dto.DepartmentUpdateRequest;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.repository.DepartmentRepository;
import com.ds.studenterp.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;
    private final FacultyRepository facultyRepository;

    public DepartmentService(DepartmentRepository repository, FacultyRepository facultyRepository) {
        this.repository = repository;
        this.facultyRepository = facultyRepository;
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department createDepartment(DepartmentRequest request) {

        Department dept = new Department();
        dept.setName(request.getName());
        dept.setCode(request.getCode());
        dept.setDescription(request.getDescription());
        dept.setActive(true);
        dept.setCreatedBy("ADMIN");
        dept.setUpdatedBy("ADMIN");

        if (request.getHodId() != null) {
            Faculty hod = facultyRepository.findById(request.getHodId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));
            dept.setHod(hod);
        }

        return repository.save(dept);
    }

    // ================= UPDATE =================
    public Department updateDepartment(Long id, DepartmentUpdateRequest request) {

        Department dept = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        dept.setName(request.getName());
        dept.setCode(request.getCode());
        dept.setDescription(request.getDescription());

        if (request.getHodId() != null) {
            Faculty hod = facultyRepository.findById(request.getHodId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));
            dept.setHod(hod);
        } else {
            dept.setHod(null);
        }

        dept.setUpdatedBy("ADMIN");

        return repository.save(dept);
    }

    public Department toggleStatus(Long id) {
        Department dept = repository.findById(id)
                .orElseThrow();
        dept.setActive(!dept.getActive());
        return repository.save(dept);
    }
}
