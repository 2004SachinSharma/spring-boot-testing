    package com.spring.testing.service;

import com.spring.testing.Entity.Student;
import com.spring.testing.dto.StudentRequest;
import com.spring.testing.exception.StudentNotFoundException;
import com.spring.testing.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


    /**
     * <h1 style="color: Green">Mockito Notes</h1>
     *
     * <h3>{@code @Mock}</h3>
     * <h2>Creates a fake object (mock) of a dependency.
     * It does not execute the real implementation.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * @Mock
     * private StudentRepository repository;
     * }
     * </pre>
     *<h2> Here, 'repository' is a fake object. No real database operations are performed.</h2>
     *
     *
     * <h3>{@code @InjectMocks}</h3>
     *<h2> Creates the real object under test and automatically injects all
     * the mock dependencies into it.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * @InjectMocks
     * private StudentService service;
     * }
     * </pre>
     * <h2>Here, 'service' is the real object, while 'repository' inside it is mocked.</h2>
     *
     *
     * <h3>{@code when().thenReturn()}</h3>
     * <h2>Defines the behavior of a mocked method.
     * It tells Mockito what should be returned when a particular method is called.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * when(repository.findById(1L))
     *         .thenReturn(Optional.of(student));
     * }
     * </pre>
     *<h2> Whenever 'findById(1L)' is called, Mockito returns the given student
     * instead of calling the real database.</h2>
     *
     *
     * <h3>{@code verify()}</h3>
     *<h2> Verifies that a particular method on the mock was actually invoked.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * verify(repository).findById(1L);
     * }
     * </pre>
     * <h2>Checks that the service called 'findById(1L)' exactly once.</h2>
     *
     *
     * <h3>{@code any()}</h3>
     *<h2> Argument matcher that accepts any object of the specified type.
     * Useful when the exact object is not important.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * when(repository.save(any(Student.class)))
     *         .thenReturn(savedStudent);
     * }
     * </pre>
     *<h2> The repository will return 'savedStudent' regardless of which Student object is passed.</h2>
     *<br>
     * <h3>{@code eq()}</h3>
     *
     * <h2>
     * An argument matcher that matches only the exact value provided.
     * Use {@code eq()} when a method argument must match a specific value.
     * </h2>
     *
     * <h3>Important Note</h3>
     *
     * <h2>
     * {@code eq()} is <b>not mandatory</b> when a method has only one exact argument.
     * In such cases, you can directly pass the expected value.
     * </h2>
     *
     * <pre>{@code
     * when(repository.save(expectedStudent))
     *         .thenReturn(savedStudent);
     * }</pre>
     *
     * <h2>
     * However, {@code eq()} becomes necessary when you combine an exact value
     * with other Mockito argument matchers such as
     * {@code any()}, {@code anyInt()}, {@code anyLong()}, etc.
     * Mockito requires that if one argument uses a matcher,
     * then <b>all arguments</b> in that method call must also use matchers.
     * </h2>
     *
     * <h3>Example</h3>
     *
     * <pre>{@code
     * findByNameAndAge(String name, int age)
     * }</pre>
     *
     * <h3>❌ Incorrect</h3>
     *
     * <pre>{@code
     * when(repository.findByNameAndAge("Sachin", anyInt()))
     *         .thenReturn(student);
     * }</pre>
     *
     * <h2>
     * This throws {@code InvalidUseOfMatchersException}
     * because the first argument is a raw value ("Sachin")
     * while the second argument is a matcher ({@code anyInt()}).
     * </h2>
     *
     * <h3>✅ Correct</h3>
     *
     * <pre>{@code
     * when(repository.findByNameAndAge(eq("Sachin"), anyInt()))
     *         .thenReturn(student);
     * }</pre>
     *
     * <h2>
     * Here, both arguments use Mockito matchers,
     * so the stubbing works correctly.
     * </h2>
     *
     * <h3>Rule of Thumb</h3>
     *
     * <h2>
     * <b>Single argument with an exact value?</b>
     * </h2>
     *
     * <pre>{@code
     * when(repository.save(expectedStudent))
     *         .thenReturn(savedStudent);
     * }</pre>
     *
     * <h2>✅ Perfectly fine. No {@code eq()} is required.</h2>
     *
     * <h2>
     * <b>Using any matcher ({@code any()}, {@code anyInt()}, etc.) in the same method call?</b>
     * Then use {@code eq()} for every argument that must match an exact value.
     * </h2>
     *
     * <pre>{@code
     * when(service.someMethod(eq("Sachin"), anyInt()))
     *         .thenReturn(student);
     * }</pre>
     *
     * <h2>✅ Correct.</h2>
     * <h3>{@code never()}</h3>
     *<h2> Verifies that a method was never called.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * verify(repository, never()).deleteById(anyLong());
     * }
     * </pre>
     *<h2> Ensures that 'deleteById()' was not executed.</h2>
     *
     *
     * <h3>{@code times(n)}</h3>
     *<h2> Verifies how many times a method was invoked.</h2>
     *
     * Example:
     * <pre>
     * {@code
     * verify(repository, times(2)).findAll();
     * }
     * </pre>
     *<h2> Confirms that 'findAll()' was called exactly two times.</h2>
     *
     */


@ExtendWith(MockitoExtension.class) //Tells JUnit to initialize Mockito before each test.
class StudentServiceTest {

    @Mock    //Creates a fake repository. As/So no database is used.
    private StudentRepository repository;

    @InjectMocks //Creates the real service and injects the mocked repository into it.
    private StudentService service;

    @Test
    void testSaveStudentSuccess() {
        Student savedStudent = Student.builder()
                .id(1L)
                .name("Sachin")
                .age(22)
                .marks(85)
                .build();

        StudentRequest request = new StudentRequest();
        request.setName(savedStudent.getName());
        request.setAge(savedStudent.getAge());
        request.setMarks(savedStudent.getMarks());


        when(repository.save(any(Student.class))).thenReturn(savedStudent);

//        return this object.

/**
 * While writing the above when(...).thenReturn(...), I had a doubt:
 *
 * Why do we use any(Student.class)?
 * Why can't we simply write:
 *
 *     when(repository.save(savedStudent)).thenReturn(savedStudent);
 *
 * or
 *
 *     when(repository.save(new Student(...))).thenReturn(savedStudent);
 *
 * The reason is that Mockito matches arguments based on what is provided in the stub.
 *
 * When we write:
 *
 *     when(repository.save(savedStudent)).thenReturn(savedStudent);
 *
 * we are telling Mockito:
 *
 * "Return savedStudent only if repository.save() is called with this exact
 * Student object (or an object considered equal according to equals())."
 *
 * However, inside service.saveStudent(request), a completely new Student
 * object is created from the request:
 *
 *     Student student = Student.builder()
 *             .name(request.getName())
 *             .age(request.getAge())
 *             .marks(request.getMarks())
 *             .build();
 *
 * This newly created object has a different reference from savedStudent.
 *
 * So the service actually executes:
 *
 *     repository.save(student);
 *
 * where 'student' is NOT the same object as 'savedStudent'.
 *
 * Therefore, the stub:
 *
 *     when(repository.save(savedStudent))
 *
 * does not match the actual call.
 *
 * To avoid matching a specific object, we write:
 *
 *     when(repository.save(any(Student.class)))
 *             .thenReturn(savedStudent);
 *
 * It gives felxibility over exact hardcoded ref in the case of objects or hardcoded value in the case of primitives by anyInt(), anyShort() etc
 *
 * Here, any(Student.class) tells Mockito:
 *
 * "I don't care which Student object is passed.
 * As long as the argument is of type Student,
 * return savedStudent."
 *
 * In other words:
 *
 *     savedStudent  --> Specific Student object
 *     any(Student.class) --> Any Student object
 *
 * This is why any(Student.class) is commonly used when the object is created
 * inside the method being tested.
 */

/**@NOTE:
 * IMPORTATN NOTE IS Although the service creates a new Student instance, eq() does not compare object references (==).
 * It uses the object's equals() method. Therefore, eq() can still work if the expected object has the same field values
 * as the object created inside the service. In practice, however, any(Student.class) is usually preferred here because
 * it avoids coupling the test to the exact contents of the object being built.*/

        Student result = service.saveStudent(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sachin", result.getName());
        assertEquals(22, result.getAge());
        assertEquals(85, result.getMarks());

        /**
         * {@Quick_Tip: we can use a AssertJ style to write these assertions}
         * Like: Below
         * */

        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Sachin")
                .hasFieldOrPropertyWithValue("age", 22)
                .hasFieldOrPropertyWithValue("marks", 85);
//        or even better
        assertThat(result)
                .extracting(
                        Student::getId,
                        Student::getName,
                        Student::getAge,
                        Student::getMarks
                )
                .containsExactly(1L, "Sachin", 22, 85);


        verify(repository).save(any(Student.class));//Checks that the method was actually called.
   //     verify(repository1).save(any(Student.class));//Would fail the test, as it verifies whether .save() of mock repository1 is called or not!, here .save() is called but not for the repository1 but for repository
    }




    @Test
    void testGetStudentSuccess() {

        Student student = Student.builder()
                .id(1L)
                .name("Rahul")
                .age(21)
                .marks(90)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.empty()); //When this method is called,


        assertEquals(Optional.empty(),
            repository.findById(1L));

        verify(repository).findById(1L); //Checks that the method was actually called.
    }

    @Test
    void testGetStudentNotFound() {

        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> service.getStudent(100L)
        );

        assertEquals(
                "Student not found with id : 100",
                exception.getMessage()
        );

        verify(repository).findById(100L);
    }

    @Test
    void testGetAllStudentsSuccess() {

        List<Student> students = List.of(
                Student.builder().id(1L).name("A").age(21).marks(90).build(),
                Student.builder().id(2L).name("B").age(22).marks(80).build()
        );

        when(repository.findAll()).thenReturn(students);

        List<Student> result = service.getAllStudents();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }

    @Test
    void testDeleteStudentSuccess() {

        when(repository.existsById(1L)).thenReturn(true);

        service.deleteStudent(1L);

        verify(repository).existsById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteStudentNotFound() {

        when(repository.existsById(5L))
                .thenReturn(false);

        assertThrows(
                StudentNotFoundException.class,
                () -> service.deleteStudent(5L)
        );

        verify(repository).existsById(5L);

        verify(repository, never())
                .deleteById(anyLong()); //Checks the method was never called. And, anyLong(): Accept any long.
    }

    @Test
    void testUpdateStudentSuccess() {

        Student existingStudent = Student.builder()
                .id(1L)
                .name("Old")
                .age(20)
                .marks(50)
                .build();

        StudentRequest request = new StudentRequest();
        request.setName("New");
        request.setAge(22);
        request.setMarks(95);

        Student updatedStudent = Student.builder()
                .id(1L)
                .name("New")
                .age(22)
                .marks(95)
                .build();

        when(repository.findById(1L))
                .thenReturn(Optional.of(existingStudent));

        when(repository.save(any(Student.class))) //any(): Accept any Student object.
                .thenReturn(updatedStudent);

        Student result = service.updateStudent(1L, request);

        assertEquals("New", result.getName());
        assertEquals(95, result.getMarks());

        verify(repository).findById(1L);

        verify(repository).save(any(Student.class));
    }

        /**
         * @Note: Always remember when doing test
         * {@code @Test}  This is a very good practice that a good unit test should be self-contained.
         *
         * Declare the input object (request), mocked return object,
         * and expected result inside the test whenever they are
         * specific to that test case.
         *
         * Use @BeforeEach only for common setup shared by multiple tests.
         */
    @Test
    void TestFindStudentByNameAndAgeSuccess() {
        StudentRequest requestStudentObj = new StudentRequest(); //Request Object
        requestStudentObj.setName("Sachin");
        requestStudentObj.setAge(21);

          Student  responseStudentObj = Student.builder().name("Sachin").age(21).build();

         Optional<List<Student>> studentListResponse = Optional.of(List.of(responseStudentObj));

    when(repository.findByNameAndAge(eq("Sachin"), anyInt())).thenReturn(studentListResponse);

 List<Student> studentss = service.findStudentByNameAndAge(requestStudentObj.getName(), requestStudentObj.getAge());

 assertAll(
         ()-> assertEquals("Sachin", studentss.getFirst().getName()),
                 () -> assertEquals(requestStudentObj.getAge(), studentss.getFirst().getAge())

 );

    }

}