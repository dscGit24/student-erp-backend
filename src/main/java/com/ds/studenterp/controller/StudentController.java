package com.ds.studenterp.controller;

import com.ds.studenterp.dto.StudentRequest;
import com.ds.studenterp.dto.StudentResponse;
import com.ds.studenterp.dto.StudentUpdateRequest;
import com.ds.studenterp.entity.Student;
import com.ds.studenterp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

//  =========== Add Student ===========
    @PostMapping
    public Student addStudent(@RequestBody StudentRequest request){
       return studentService.createStudent(request);
    }

//  =========== Fetch All Students ===========
    @GetMapping
    public List<StudentResponse> getAllStudents(){
        return studentService.getAllStudents()
                .stream().map(StudentResponse::new)
                .toList();
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
            @RequestBody StudentUpdateRequest request) {

        return ResponseEntity.ok(
                studentService.updateStudent(id, request)
        );
    }

//  =========== (Soft) Delete Student ===========
    @PatchMapping("/{id}/status")
    public ResponseEntity<Student> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.toggleStatus(id));
    }
}