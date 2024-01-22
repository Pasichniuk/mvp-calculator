package com.mvp.calculator.service.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.experimental.UtilityClass;

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

    private static final CSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();

    public static List<String> getNamesOfCsvFilesInDirectory(String dirPath) {
        try (var paths = Files.walk(Paths.get(dirPath))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(path -> path.endsWith(CSV_EXTENSION))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from directory: %s", e); // TODO: throw custom exception
        }
    }

    public static void verifyNonCsvFilesAbsentInDirectory(String dirPath) {
        try (var paths = Files.walk(Paths.get(dirPath))) {
            boolean nonCsvFilesArePresentInDir = paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .anyMatch(path -> !path.endsWith(CSV_EXTENSION));
            if (nonCsvFilesArePresentInDir) {
                throw new IllegalStateException("It is prohibited for non-CSV files to be present in the input directory");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from directory: %s", e); // TODO: throw custom exception
        }
    }

    public static List<String[]> readDataFromCsv(String fileName) {
        try (var fileReader = new FileReader(fileName);
             var csvReader = new CSVReaderBuilder(fileReader)
                     .withCSVParser(CSV_PARSER)
                     .build()) {
            return csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(String.format("Failed to read data from file: %s", fileName), e); // TODO: throw custom exception
        }
    }
}
