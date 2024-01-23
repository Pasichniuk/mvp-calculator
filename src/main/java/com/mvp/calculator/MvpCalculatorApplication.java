package com.mvp.calculator;

import com.mvp.calculator.service.MvpCalculatorService;
import com.mvp.calculator.service.util.CsvFileUtils;

public class MvpCalculatorApplication {

    private static final String INPUT_DIR_PATH = "/Users/pasichniuk/IdeaProjects/mvp-calculator/csv-input";

    public static void main(String[] args) {
        CsvFileUtils.verifyNonCsvFilesAbsentInDirectory(INPUT_DIR_PATH); // requirement
        var fileNames = CsvFileUtils.getNamesOfCsvFilesInDirectory(INPUT_DIR_PATH);

        var mvpCalculatorService = new MvpCalculatorService();
        mvpCalculatorService.calculateMvp(fileNames);
    }
}