package com.ubs.test.output;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ubs.test.objects.AccountType;
import com.ubs.test.objects.EODPosition;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVGeneratorTest {

    EODPosition ibmPosition1 = new EODPosition("IBM",101, AccountType.E,1001000,1000);
    EODPosition ibmPosition2 = new EODPosition("IBM",201, AccountType.I,2001000,2000);

    EODPosition applPosition1 = new EODPosition("APPL",101, AccountType.E,3001000,3000);
    EODPosition applPosition2 = new EODPosition("APPL",201, AccountType.I,4001000,4000);


    @Test
    public void generateCSVWithCorrectOrder() throws Exception {
        CSVGenerator generator = new CSVGenerator();
        ArrayList<EODPosition> ibmPositions = Lists.newArrayList(ibmPosition1,ibmPosition2);
        ArrayList<EODPosition> applPositions = Lists.newArrayList(applPosition1,applPosition2);
        Map<String,ArrayList<EODPosition>> eodPositions = ImmutableMap.of("IBM",ibmPositions,"APPL",applPositions);
        List<String> instrumentOrder = Lists.newArrayList("APPL","IBM");
        generator.generateCSV(eodPositions,instrumentOrder,"src/test/resources/EODFinal-Test1.txt" );
        List<String> result;
        try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/EODFinal-Test1.txt")).skip(1)) {
            result = stream.map(x -> Arrays.asList(x.split(",")))
                    .map(m -> m.get(0))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw e;
        }
        Assert.assertEquals(result.get(0),"APPL");
        Assert.assertEquals(result.get(1),"APPL");
        Assert.assertEquals(result.get(2),"IBM");
        Assert.assertEquals(result.get(3),"IBM");
    }

}