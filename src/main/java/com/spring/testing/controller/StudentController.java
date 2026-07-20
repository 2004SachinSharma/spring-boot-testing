package com.spring.testing.controller;

import com.spring.testing.Entity.Student;
import com.spring.testing.dto.StudentRequest;
import com.spring.testing.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequest request) {
        return new ResponseEntity<>(service.saveStudent(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudent(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<Student> getStudent(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getStudent(name));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody StudentRequest request) {

        return ResponseEntity.ok(service.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        service.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

}