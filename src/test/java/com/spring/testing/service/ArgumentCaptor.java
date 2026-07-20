package com.spring.testing.service;


import com.spring.testing.Entity.Student;
import com.spring.testing.dto.StudentRequest;
import com.spring.testing.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArgumentCaptor {

    @Mock
    StudentRepository repository;

    @InjectMocks
    StudentService studentService;

/**
 * ArgumentCaptor
 * Why do we need it?
 *
 * Sometimes it's not enough to verify that a method was called. You also want to check what object or value was passed to that method.
 * Whether the logic runs perfect to get the proper argument or value, pre-passing the value to the next component (here repository).
 * like I want to save the name in all captial letters, while saving the Student!
 *
 * So we can verify that while the value being passed, is it in the correct form, how business logic requires it to be!
 * */
    @Captor
org.mockito.ArgumentCaptor<Student> captor;

    @Test
    @DisplayName("should save student successfully")
    void shouldSuccessfullySaveStudent() {
        StudentRequest student = new StudentRequest();
        student.setName("Sapphire");
        student.setAge(20);
        student.setMarks(80);


        when(repository.save(any(Student.class)))
                .thenReturn(new Student()
                        .builder()
                        .name("Sapphire")
                        .age(20)
                        .marks(80)
                        .build());


        Student savedStudent = studentService.saveStudent(student);

        verify(repository).save(captor.capture());

        String capturedStudentName = captor.getValue().getName();

        assertEquals("Sapphire", capturedStudentName); //Actually, we already have a saveStudent API written,
//        which saves the student without casting it from LOWER to UPPER case, so I don't wanted to change the logic as
//        other tests depends on it, they may break! but you are free to test so, by writing the logic to convert the
//        name of the student to Upper case before passing to the repository.save(S Entity), and verify that the name in
//        uppercase being passed to the repository by using the Argument captor.

//        Not limiting to this we can go on testing with other APIs as well as the project grows and business logic requires for other
//        values to process to some state before passing from one part to another




    }

    /**
     * When to use
     * Verify transformed objects
     * Verify DTO → Entity mapping
     * Verify generated IDs
     * Verify audit fields
     * Verify timestamps*/


}
/**
 * Code Coverage (JaCoCo)
 * What is Code Coverage?
 *
 * It tells you:
 *
 * "How much of your application code is executed by your tests?"
 *
 * Example:
 *
 * StudentService.java
 *
 * saveStudent()      ✔ Tested
 *
 * deleteStudent()    ❌ Not Tested
 *
 * updateStudent()    ✔ Tested
 *
 * Coverage = 66%
 * Common Coverage Metrics
 * Line Coverage
 * Branch Coverage
 * Method Coverage
 * Class Coverage
 * Is 100% coverage required?
 *
 * No.
 *
 * Aim for:
 *
 * 70–80% for many projects
 * 80–90% for critical business logic
 *
 * Where to find?
 * In modern IDEs it can be easily found in plugin marketplace
 *
 * Remember:
 *
 * Good tests are more important than high percentages.*/

// Some more topics like Spy(), doReturn() etc left from the plan! they are not covered here as they are rarely to not used, in modern testing scenarios.
// If the requirement comes, then it will be definitely covered