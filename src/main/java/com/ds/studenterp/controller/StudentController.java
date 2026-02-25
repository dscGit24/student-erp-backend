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

    @PostMapping
    public Student addStudent(@RequestBody Student student){
       return studentService.saveStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent){
        return studentService.updateStudent(id, updatedStudent);
//        try {
//            return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }
}