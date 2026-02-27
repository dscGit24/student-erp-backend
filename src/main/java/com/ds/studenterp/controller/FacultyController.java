package com.ds.studenterp.controller;

import com.ds.studenterp.entity.Faculty;
import com.ds.studenterp.service.FacultyService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    // ================= READ ALL =================
    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    // ================= READ ONE =================
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(
            @PathVariable Long id,
            @RequestBody Faculty updated) {

        return ResponseEntity.ok(
                facultyService.updateFaculty(id, updated)
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