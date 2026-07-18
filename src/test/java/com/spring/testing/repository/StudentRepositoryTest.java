package com.spring.testing.repository;

import com.spring.testing.Entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;
    Student student;

    @BeforeEach
    void setUp() {

        student = new Student();
        student.setName("Sachin");
        student.setAge(30);
        student.setMarks(100);

        repository.save(student);
    }

    @DisplayName("testing findByName of repository, passed some name, should return the Optional<Student> object successfully matching with the passed name")
    @Test
    void findByNameShouldReturnOptionalContainingStudent() {
        String name = "Sachin";

        Optional<Student> students = repository.findByName(name);

        Assertions.assertTrue(students.isPresent());
        assertEquals("Sachin", students.get().getName());


    }

    @DisplayName("testing findByName of repository, passed some name, should return the Optional.empty() object successfully when no record with the requested name matches in the DB table")
    @Test
    void findByNameShouldReturnEmptyOptionalWhenNoRecordWithTheRequestedNameMatchesInTheDBTable() {
        String name = "Naman";

        Optional<Student> students = repository.findByName(name);

        assertFalse(students.isPresent());
        Exception exception = Assertions.assertThrows(

                Exception.class, () -> {
                    students.get().getName();
                }
        );

        System.out.println(exception.getMessage());

        Assertions.assertTrue(exception.getMessage().contains("No value present"));

    }

    @Test
    void shouldDeleteStudent() {

        repository.delete(student);

        Optional<Student> result =
                repository.findById(student.getId());

        assertFalse(result.isPresent());

    }

    @Test
    void shouldFindStudentsByMarks() {

        repository.save(
                Student.builder()
                        .name("Sachin")
                        .marks(87)
                        .age(21)
                        .build());

        repository.save(
                Student.builder()
                        .name("Rahul")
                        .marks(91)
                        .age(20)
                        .build());

        repository.save(
                Student.builder()
                        .name("Aman")
                        .marks(91)
                        .age(22)
                        .build());

        Optional<List<Student>> students =
                repository.findByMarks(91);

        assertEquals(2, students.get().size());

    }

    /**
     * <h3>{@code @DataJpaTest}</h3>
     *
     * Loads only JPA-related components for testing repositories.
     *
     * Features:
     * - Uses an in-memory H2 database by default.
     * - Loads repository beans.
     * - Does not load controllers or services.
     * - Rolls back each test automatically.
     *
     * Best Use:
     * Test CRUD operations, custom finder methods,
     * JPQL queries, native queries, and repository behavior.
     */


}