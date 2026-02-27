package com.ds.studenterp.service;

import com.ds.studenterp.entity.Department;
import com.ds.studenterp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department save(Department dept) {
        return repository.save(dept);
    }

    public Department toggleStatus(Long id) {
        Department dept = repository.findById(id)
                .orElseThrow();
        dept.setActive(!dept.getActive());
        return repository.save(dept);
    }
}
