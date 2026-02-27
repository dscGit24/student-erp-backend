package com.ds.studenterp.controller;

import com.ds.studenterp.entity.Student;
import com.ds.studenterp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost:5173")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

//  =========== Add Student ===========
    @PostMapping
    public Student addStudent(@RequestBody Student student){
       return studentService.saveStudent(student);
    }

//  =========== Fetch All Students ===========
    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

//  =========== Fetch One Student ===========
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    //  =========== Update Student ===========
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, updatedStudent)
        );
    }

//  =========== (Soft) Delete Student ===========
    @PatchMapping("/{id}/status")
    public ResponseEntity<Student> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.toggleStatus(id));
    }
}