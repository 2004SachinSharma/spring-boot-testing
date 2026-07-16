package com.spring.testing.service;

import com.spring.testing.Entity.Student;
import com.spring.testing.dto.StudentRequest;
import com.spring.testing.exception.StudentNotFoundException;
import com.spring.testing.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public Student saveStudent(StudentRequest request) {
        Student student = Student.builder()
                .name(request.getName())
                .age(request.getAge())
                .marks(request.getMarks())
                .build();

        return repository.save(student);
    }

    public Student getStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found with id : " + id));
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void deleteStudent(Long id) {

        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id : " + id);
        }

        repository.deleteById(id);
    }

    public Student updateStudent(Long id, StudentRequest request) {

        Student existingStudent = getStudent(id);

        existingStudent.setName(request.getName());
        existingStudent.setAge(request.getAge());
        existingStudent.setMarks(request.getMarks());

        return repository.save(existingStudent);
    }

    public String calculateGrade(int marks) {

        if (marks < 0 || marks > 100)
            return "Invalid";

        if (marks >= 90)
            return "A";

        if (marks >= 75)
            return "B";

        if (marks >= 50)
            return "C";

        if (marks >= 35)
            return "D";

        return "F";
    }

    public boolean isEligibleForPlacement(int age, int marks) {
        return age >= 21 && marks >= 60;
    }

}