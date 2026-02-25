package com.ds.studenterp.service;

import com.ds.studenterp.entity.Student;
import com.ds.studenterp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Student saveStudent(Student student){
        long count = studentRepository.count() + 1;

        String enrollment = "STU" + LocalDate.now().getYear() + String.format("%04d", count); // STU20260001
        student.setEnrollmentNumber(enrollment);

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findByDeletedFalse();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found!.."));
        student.setDeleted(true);
        student.setDeletedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }
    public long getTotalStudents(){
        return studentRepository.count();
    }

    public Student updateStudent(Long id, Student updatedStudent) {

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setEnrollmentNumber(updatedStudent.getEnrollmentNumber());
        existing.setCourse(updatedStudent.getCourse());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setAddress(updatedStudent.getAddress());
        existing.setAadharNumber(updatedStudent.getAadharNumber());
        existing.setGender(updatedStudent.getGender());

        // Only update photo if new one is provided
        if (updatedStudent.getPhoto() != null) {
            existing.setPhoto(updatedStudent.getPhoto());
        }

        if (updatedStudent.getDeleted() != null) {
            existing.setDeleted(updatedStudent.getDeleted());
        }

        return studentRepository.save(existing);
    }
}
