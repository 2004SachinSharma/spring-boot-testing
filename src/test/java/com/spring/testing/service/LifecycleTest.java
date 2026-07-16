package com.spring.testing.service;

import com.spring.testing.service.StudentService;
import org.junit.jupiter.api.*;

class LifecycleTest {

    private StudentService service;

    /**
     * /*
     * @BeforeAll
     * Runs only once before all test methods.
     *
     * @BeforeEach
     * Runs before every test method.
     *
     * @AfterEach
     * Runs after every test method.
     *
     * @AfterAll
     * Runs only once after all tests complete.
     * */

    @BeforeAll
    @DisplayName("Would Execute before each test")

    static void beforeAllTests() {
        System.out.println("===== Before All Tests =====");
    }

    @AfterAll
    @DisplayName("Would Execute after all tests")

    static void afterAllTests() {
        System.out.println("===== After All Tests =====");
    }

    @BeforeEach
    @DisplayName("Would Execute before each test")

    void setUp() {
        System.out.println("Creating StudentService object...");
        service = new StudentService(null);
    }

    @AfterEach
    @DisplayName("Would Execute after each test")

    void tearDown() {
        System.out.println("Cleaning resources...\n");
    }

    @Test
    @DisplayName("test should return proper grade")
    void testCalculateGradeA() {

        String grade = service.calculateGrade(95);

        Assertions.assertEquals("A", grade);

        System.out.println("Creating StudentService object...");

    }

    @Test
    @DisplayName("test should result true")
    void testPlacementEligibility() {

        boolean eligible = service.isEligibleForPlacement(22, 75);

        Assertions.assertTrue(eligible);

        System.out.println("testing Placement Eligibility...");

    }
}