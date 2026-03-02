package com.ds.studenterp.controller;

import com.ds.studenterp.dto.DepartmentRequest;
import com.ds.studenterp.dto.DepartmentResponse;
import com.ds.studenterp.dto.DepartmentUpdateRequest;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<DepartmentResponse> getAll() {
        return service.getAll()
                .stream()
                .map(DepartmentResponse::new)
                .toList();
    }

    @PostMapping
    public Department save(@RequestBody DepartmentRequest request) {
        return service.createDepartment(request);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @RequestBody DepartmentUpdateRequest request) {
        return service.updateDepartment(id, request);
    }

    @PatchMapping("/{id}/status")
    public Department toggle(@PathVariable Long id) {
        return service.toggleStatus(id);
    }
}
