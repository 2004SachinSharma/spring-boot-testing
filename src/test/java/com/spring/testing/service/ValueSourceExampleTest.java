package com.spring.testing.service;

import com.spring.testing.service.StudentService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ValueSourceExampleTest {

    private final StudentService service = new StudentService(null);

    /**
     * Note on JUnit 5 ParameterizedTest Providers:
     * <p>
     * {@code @ValueSource} CANNOT replace {@code @CsvSource}.
     * {@code @ValueSource} is strictly limited to providing a single argument
     * of a single type per test invocation (e.g., an array of Strings or ints).
     * <p>
     * In contrast, {@code @CsvSource} provides multiple arguments of
     * different types per invocation by parsing comma-separated strings.
     * Use {@code @CsvSource} when your test requires composite inputs
     * (like {@code String} and {@code int} together), and use {@code @ValueSource}
     * when iterating through single-value tests.
     *
     * @see org.junit.jupiter.params.provider.ValueSource
     * @see org.junit.jupiter.params.provider.CsvSource
     */



    @ParameterizedTest
    /**
     * @ValueSource
     * Used when only one input parameter
     * changes across multiple test executions.
     *
     **/
    @ValueSource(ints = {90, 91, 95, 99, 100})
    void shouldReturnGradeA(int marks) {

        assertEquals("A", service.calculateGrade(marks));
    }

}


