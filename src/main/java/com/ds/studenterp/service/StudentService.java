package com.ds.studenterp.service;

import com.ds.studenterp.dto.StudentRequest;
import com.ds.studenterp.dto.StudentUpdateRequest;
import com.ds.studenterp.entity.Course;
import com.ds.studenterp.entity.Department;
import com.ds.studenterp.entity.FeeStructure;
import com.ds.studenterp.entity.Student;
import com.ds.studenterp.entity.StudentFee;
import com.ds.studenterp.repository.CourseRepository;
import com.ds.studenterp.repository.DepartmentRepository;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // ================= CREATE =================
    @Transactional
    public Student createStudent(StudentRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAadharNumber(request.getAadharNumber());
        student.setDepartment(department);
        student.setCourse(course);
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setPhoto(request.getPhoto());

        return saveStudent(student);
    }

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
    public Student updateStudent(Long id, StudentUpdateRequest request) {

        Student existing = getStudentById(id);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setCourse(course);
        existing.setDepartment(department);
        existing.setAddress(request.getAddress());
        existing.setAadharNumber(request.getAadharNumber());
        existing.setGender(request.getGender());

        if (request.getPhoto() != null) {
            existing.setPhoto(request.getPhoto());
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