package main;

import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import data.Factory;
import data.input.InputData;
import data.output.AnnualChildren;

import java.io.File;
import java.io.IOException;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 1; i <= 30/*Constants.TESTS_NUMBER*/; i++) {
            InputData inputData = objectMapper.readValue(
                    new File("tests/test" + i + ".json"), InputData.class);
            AnnualChildren annualChildren = Factory.processData(inputData);
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
            objectWriter.writeValue(new File("output/out_" + i + ".json"), annualChildren);
        }
        Checker.calculateScore();
    }
}
