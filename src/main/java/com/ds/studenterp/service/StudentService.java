package com.ds.studenterp.service;

import com.ds.studenterp.entity.FeeStructure;
import com.ds.studenterp.entity.Student;
import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.repository.FeeStructureRepository;
import com.ds.studenterp.repository.StudentFeeRepository;
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

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private StudentFeeRepository studentFeeRepository;

    // ================= CREATE =================
    @Transactional
    public Student saveStudent(Student student) {

        long count = studentRepository.count() + 1;
        String enrollment = "STU" + LocalDate.now().getYear()
                + String.format("%04d", count);

        student.setEnrollmentNumber(enrollment);
        student.setActive(true);
        student.setCreatedBy("ADMIN");
        student.setUpdatedBy("ADMIN");

        Student savedStudent = studentRepository.save(student);

        // 🔥 AUTO CREATE FINANCE RECORD

        FeeStructure feeStructure = feeStructureRepository
                .findByCourse(savedStudent.getCourse())
                .orElseThrow(() ->
                        new RuntimeException("No fee structure found for course"));

        StudentFee studentFee = new StudentFee();
        studentFee.setStudent(savedStudent);
        studentFee.setFeeStructure(feeStructure);
        studentFee.setTotalAmount(feeStructure.getTotalAmount());
        studentFee.setBalanceAmount(feeStructure.getTotalAmount());
        studentFee.setStatus("PENDING");

        studentFeeRepository.save(studentFee);

        return savedStudent;
    }

    // ================= READ =================
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student Not Found!..")
        );
    }

    // ================= UPDATE =================
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existing = getStudentById(id);

        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setCourse(updatedStudent.getCourse());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setAddress(updatedStudent.getAddress());
        existing.setAadharNumber(updatedStudent.getAadharNumber());
        existing.setGender(updatedStudent.getGender());

        if (updatedStudent.getPhoto() != null) {
            existing.setPhoto(updatedStudent.getPhoto());
        }

        existing.setUpdatedBy("ADMIN");

        return studentRepository.save(existing);
    }

    // ================= SOFT DELETE =================
    public Student toggleStatus(Long id) {

        Student student = getStudentById(id);

        if (student.getActive()) {
            // SOFT DELETE
            student.setActive(false);
            student.setDeletedAt(LocalDateTime.now());
            student.setDeletedBy("ADMIN");
        } else {
            // RESTORE
            student.setActive(true);
            student.setDeletedAt(null);
            student.setDeletedBy(null);
        }

        student.setUpdatedBy("ADMIN");

        return studentRepository.save(student);
    }

    // ================= STATS =================
    public long getTotalStudents() {
        return studentRepository.count();
    }
}