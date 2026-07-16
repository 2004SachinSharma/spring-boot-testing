package com.spring.testing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    /*
        We pass null because the methods tested below
        (calculateGrade and isEligibleForPlacement)
        do not use the repository.
         * If a tested method depends on the repository,
         * we should provide a mocked repository using Mockito
         * instead of passing null.
     */
    private final StudentService service = new StudentService(null); //we can indeed mock the repository but not done intentionally

    @Test
    @DisplayName("Should return grade A when marks are greater than or equal to 90")
    void testCalculateGradeA() {

        String actualGrade = service.calculateGrade(95);

        assertEquals("A", actualGrade);

        //Test 1
//        boolean isEligible = service.isEligibleForPlacement(22, 70);
//        assertTrue(isEligible);

        //Test 2
//        StudentRequest sachin = new StudentRequest();
//        sachin.setName("sachin");
//        sachin.setAge(32);
//        sachin.setMarks(90);
//
//        assertThrows(Exception.class,()->{service.updateStudent(101L,sachin);});

        //Test 3
//
//        String statusInvalid = service.calculateGrade(-1);
//        assertEquals("Invalid",statusInvalid );


        //Test 4
//
//        String statusEligible = service.calculateGrade(-1);
//        assertEquals("Invalid",statusInvalid );



    }

    @Test
    @DisplayName("Should return grade B")
    void testCalculateGradeB() {

        String actualGrade = service.calculateGrade(80);

        assertEquals("B", actualGrade);
    }

    @Test
    @DisplayName("Should return grade C")
    void testCalculateGradeC() {

        String actualGrade = service.calculateGrade(60);

        assertEquals("C", actualGrade);
    }

    @Test
    @DisplayName("Should return grade D")
    void testCalculateGradeD() {

        String actualGrade = service.calculateGrade(40);

        assertEquals("D", actualGrade);
    }

    @Test
    @DisplayName("Should return grade F")
    void testCalculateGradeF() {

        String actualGrade = service.calculateGrade(20);

        assertEquals("F", actualGrade);
    }

    @Test
    @DisplayName("Should return Invalid when marks are negative")
    void testNegativeMarks() {

        String actualGrade = service.calculateGrade(-10);

        assertEquals("Invalid", actualGrade);
    }

    @Test
    @DisplayName("Should return Invalid when marks are greater than 100")
    void testMarksGreaterThanHundred() {

        String actualGrade = service.calculateGrade(120);

        assertEquals("Invalid", actualGrade);
    }

    @Test
    @DisplayName("Student is eligible for placement")
    void testEligibleStudent() {

        boolean eligible = service.isEligibleForPlacement(22, 75);

        assertTrue(eligible);
    }

    @Test
    @DisplayName("Student is not eligible for placement")
    void testNotEligibleStudent() {

        boolean eligible = service.isEligibleForPlacement(19, 55);

        assertFalse(eligible);
    }

    @Test
    @DisplayName("Multiple assertions together")
    void testUsingAssertAll() {

        assertAll(
                () -> assertEquals("A", service.calculateGrade(92)),
                () -> assertEquals("B", service.calculateGrade(80)),
                () -> assertEquals("C", service.calculateGrade(55)),
                () -> assertEquals("D", service.calculateGrade(36)),
                () -> assertEquals("F", service.calculateGrade(10))
        );
    }

    @Test
    @DisplayName("Method should not throw any exception")
    void testAssertDoesNotThrow() {

        assertDoesNotThrow(() -> service.calculateGrade(90));
    }

    @Test
    @DisplayName("Example of assertThrows")
    void testAssertThrows() {

        ArithmeticException exception = assertThrows(
                ArithmeticException.class,
                () -> {
                    int result = 10 / 0;
                }
        );

        assertEquals("/ by zero", exception.getMessage());
    }

}