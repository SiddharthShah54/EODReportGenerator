package com.ubs.test.input;

import com.ubs.test.objects.SODPosition;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InputFileReaderTest {
    @Test(expected = NoSuchFileException.class)
    public void getSodPositionWithNoFilePresent() throws Exception {
        InputFileReader reader =new InputFileReader();
        reader.getSodPosition("src/test/resources/1.txt");
    }

    @Test
    public void getSodPositionWithEmptyFile() throws Exception {
        InputFileReader reader =new InputFileReader();
        Map<String, ArrayList<SODPosition>> sodPosition = reader.getSodPosition("src/test/resources/SOD-Empty.txt");
        Assert.assertTrue(sodPosition.size() == 0);
    }

    @Test
    public void getSodPositionWithOneInvalidRecord() throws Exception {
        InputFileReader reader =new InputFileReader();
        Map<String, ArrayList<SODPosition>> sodPosition = reader.getSodPosition("src/test/resources/SOD-Invalid.txt");
        Assert.assertTrue(sodPosition.size() == 0);
    }

    @Test
    public void getValidSodPosition() throws Exception {
        InputFileReader reader =new InputFileReader();
        Map<String, ArrayList<SODPosition>> sodPosition = reader.getSodPosition("src/test/resources/SOD-ValidPositions.txt");
        Assert.assertTrue(sodPosition.size() == 5);
    }

    @Test
    public void validateCorrectValue() {
        List<String> enteredLine = Arrays.asList("IBM","101","E","1000000");
        InputFileReader reader =new InputFileReader();
        Assert.assertTrue(reader.validate(enteredLine));
    }

    @Test
    public void validateIncorrectValue() {
        List<String> enteredLine = Arrays.asList("IBM","101","E","1000000","abcde");
        InputFileReader reader =new InputFileReader();
        Assert.assertFalse(reader.validate(enteredLine));
    }

    @Test
    public void validateValidAccountType() {
        List<String> enteredLine = Arrays.asList("IBM","101","P","1000000");
        InputFileReader reader =new InputFileReader();
        Assert.assertFalse(reader.validate(enteredLine));
    }
}