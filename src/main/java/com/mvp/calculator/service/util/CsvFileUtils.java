package com.mvp.calculator.service.util;

import com.mvp.calculator.exception.DirectoryReadFailedException;
import com.mvp.calculator.exception.InvalidFileContentException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CsvFileUtils {

    private static final String CSV_EXTENSION = ".csv";
    private static final char CSV_SEPARATOR = ';';
    private static final int FILES_LIMIT = 20; // configurable
    private static final long FILE_SIZE_LIMIT = 10 * 1024 * 1024; // configurable

    private static final CSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

    public static List<String> getNamesOfCsvFilesInDirectory(String dirPath) {
        try (var paths = Files.walk(Paths.get(dirPath))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(path -> path.endsWith(CSV_EXTENSION))
                    .limit(FILES_LIMIT)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new DirectoryReadFailedException(dirPath, e.getMessage());
        }
    }

    public static void verifyNonCsvFilesAbsentInDirectory(String dirPath) {
        try (var paths = Files.walk(Paths.get(dirPath))) {
            boolean nonCsvFilesArePresentInDir = paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .anyMatch(path -> !path.endsWith(CSV_EXTENSION));
            if (nonCsvFilesArePresentInDir) {
                var message = "It is prohibited for non-CSV files to be present in the input directory";
                throw new DirectoryReadFailedException(dirPath, message);
            }
        } catch (IOException e) {
            throw new DirectoryReadFailedException(dirPath, e.getMessage());
        }
    }

    public static List<String[]> readDataFromCsv(String fileName) {
        var file = new File(fileName);
        long size = file.length();
        if (size > FILE_SIZE_LIMIT) {
            throw new InvalidFileContentException("File size exceeds the limit of 10 MB");
        }

        try (var fileReader = new FileReader(fileName);
             var csvReader = new CSVReaderBuilder(fileReader)
                     .withCSVParser(CSV_PARSER)
                     .build()) {
            return csvReader.readAll();
        } catch (IOException | CsvException e) {
            var message = "Failed to read data from file '%s' due to: %s";
            throw new InvalidFileContentException(String.format(message, fileName, e.getMessage()));
        }
    }
}
