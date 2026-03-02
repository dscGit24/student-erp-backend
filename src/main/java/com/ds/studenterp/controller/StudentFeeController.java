package com.ds.studenterp.controller;

import com.ds.studenterp.dto.StudentFeeResponse;
import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.service.StudentFeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-fees")
public class StudentFeeController {

    private final StudentFeeService service;

    public StudentFeeController(StudentFeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentFeeResponse> getAll() {
        return service.getAll()
                .stream().map(StudentFeeResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public StudentFee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PatchMapping("/{id}/status")
    public StudentFee toggle(@PathVariable Long id) {
        return service.toggleStatus(id);
    }
}