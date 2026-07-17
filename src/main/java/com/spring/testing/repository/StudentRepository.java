package com.spring.testing.repository;

import com.spring.testing.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByName(String name);
    Optional<List<Student>> findByNameAndAge(String name, int age);
}
