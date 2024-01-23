package com.mvp.calculator;

import com.mvp.calculator.service.MvpCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MvpCalculatorServiceTest {

    private static final String INPUT_DIR_PATH = "/Users/pasichniuk/IdeaProjects/mvp-calculator/csv-input";

    @Test
    void shouldReturnCorrectMvp() {
        // Given
        var expectedMvp = "nick3";
        var mvpCalculatorService = new MvpCalculatorService();

        // When
        var actualMvp = mvpCalculatorService.calculateMvp(INPUT_DIR_PATH);

        // Then
        assertEquals(expectedMvp, actualMvp);
    }
}
