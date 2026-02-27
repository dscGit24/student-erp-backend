package com.ds.studenterp.service;

import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.repository.StudentFeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentFeeService {

    private final StudentFeeRepository repository;

    public StudentFeeService(StudentFeeRepository repository) {
        this.repository = repository;
    }

    public List<StudentFee> getAll() {
        return repository.findAll();
    }

    public StudentFee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student fee not found"));
    }

    public StudentFee toggleStatus(Long id) {
        StudentFee fee = getById(id);
        fee.setActive(!fee.getActive());
        return repository.save(fee);
    }
}