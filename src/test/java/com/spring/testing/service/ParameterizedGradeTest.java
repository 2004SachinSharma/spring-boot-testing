package com.spring.testing.service;

import com.spring.testing.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterizedGradeTest {

    private final StudentService service = new StudentService(null);

    /** <div style="color:red;">
     * Now instead of writing
     * <br>
     * {@code assertEquals("A", service.calculateGrade(95));}
     * <br>
     * {@code assertEquals("B", service.calculateGrade(80));}
     * <br>
     * {@code assertEquals("C", service.calculateGrade(55));}
     * <br>
     * {@code assertEquals("F", service.calculateGrade(20));}
     * <br>
     * JUnit lets us write one test that runs multiple times with different inputs.
     *</div>
     *  */


    @ParameterizedTest
    @DisplayName("Grade should be calculated correctly")

    /**
     * How does @CsvSource works
     * @CsvSource({
     *     "95, A",
     *     "80, B",
     *     "50, C"
     * })
     *
     * JUnit automatically executes:
     *
     * testCalculateGrade(95, "A")
     *
     * ↓
     *
     * testCalculateGrade(80, "B")
     *
     * ↓
     *
     * testCalculateGrade(50, "C")
     *
     * */

    @CsvSource({
            "95, A",
            "90, A",
            "80, B",
            "75, B",
            "60, C",
            "50, C",
            "40, D",
            "35, D",
            "20, F",
            "-1, Invalid",
            "101, Invalid"
    })

    void testCalculateGrade(int marks, String expectedGrade) {

        String actualGrade = service.calculateGrade(marks);

        assertEquals(expectedGrade, actualGrade);
    }

     //Let's see one more

    @ParameterizedTest
    @DisplayName("age and marks should be eligible for placement and hence be true")
    @CsvSource({
            "21, 60",
            "25, 89",
            "22, 65",
            "21, 90",
            "26, 62",
            "24, 80",

    })

    void testIsEligibleForPlacement(int age, int marks) {

        boolean isEligibleForPlacement = service.isEligibleForPlacement(age, marks);

        Assertions.assertTrue(isEligibleForPlacement);

    }

}

/**
 * @CsvSource
 * Used when every test execution requires
 * multiple input values or an input and an expected output.
 *
 * NOTE:
 * Each @ParameterizedTest method requires its own dedicated data set. If you need to test a different logical flow
 * or a different method signature, you must write a separate test method with its own source annotation.
 *
 *i.e.
 *
 * // ❌ THIS WILL FAIL
 * @ParameterizedTest
 * @CsvSource({
 *     "1, 'apple'",   // Works for testMethod(int id, String name)
 *     "2, 'banana'",  // Works for testMethod(int id, String name)
 *     "'cherry', 3"   // ❌ FAILS: Logically/structurally incompatible!
 * })
 * void testMethod(int id, String name) {
 *     // JUnit cannot map 'cherry' to an int
 * }
 *
 * */

