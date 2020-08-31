package com.example.crudexample.controller;

import com.example.crudexample.entity.Student;
import com.example.crudexample.exception.ResourceNotFoundException;
import com.example.crudexample.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // get all students
    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    // get student by id
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable(value = "id") Integer student_id) {
        return this.studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + student_id));

    }

    // create student
    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        return this.studentRepository.save(student);
    }

    // update student
    @PutMapping("/student/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable("id") Integer student_id) {
        Student existing = this.studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + student_id));
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setGender(student.getGender());
        existing.setAddress(student.getAddress());
        return this.studentRepository.save(existing);
    }

    //delete student by id
    @DeleteMapping("/student/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer student_id) {
        Student existing = this.studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + student_id));
        this.studentRepository.delete(existing);
        return ResponseEntity.ok().build();
    }
}
