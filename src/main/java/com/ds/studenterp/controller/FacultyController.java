package com.ds.studenterp.controller;

import com.ds.studenterp.dto.FacultyRequest;
import com.ds.studenterp.dto.FacultyResponse;
import com.ds.studenterp.dto.FacultyUpdateRequest;
import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.service.FacultyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    // ================= READ ALL =================
    @GetMapping
    public List<FacultyResponse> getAllFaculty() {
        return facultyService.getAllFaculties()
                .stream()
                .map(FacultyResponse::new)
                .toList();
    }

    // ================= READ ONE =================
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<?> createFaculty(@RequestBody FacultyRequest request) {

        Faculty faculty = facultyService.createFaculty(request);
        return ResponseEntity.ok(faculty);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(
            @PathVariable Long id,
            @RequestBody FacultyUpdateRequest request) {

        return ResponseEntity.ok(
                facultyService.updateFaculty(id, request)
        );
    }

    // ================= SOFT DELETE (TOGGLE) =================
    @PatchMapping("/{id}/status")
    public ResponseEntity<Faculty> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(
                facultyService.toggleStatus(id)
        );
    }
}