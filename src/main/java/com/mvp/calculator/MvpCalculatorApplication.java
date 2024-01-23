package com.mvp.calculator;

import com.mvp.calculator.service.MvpCalculatorService;

public class MvpCalculatorApplication {

    private static final String INPUT_DIR_PATH = "/Users/pasichniuk/IdeaProjects/mvp-calculator/csv-input";

    public static void main(String[] args) {
        var mvpCalculatorService = new MvpCalculatorService();
        String mvp = mvpCalculatorService.calculateMvp(INPUT_DIR_PATH);
        System.err.printf("%n======= MVP is %s =======%n%n", mvp);
    }
}