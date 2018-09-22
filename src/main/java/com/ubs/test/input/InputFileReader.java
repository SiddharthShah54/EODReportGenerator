package com.ubs.test.input;

import com.google.common.collect.Lists;
import com.ubs.test.objects.AccountType;
import com.ubs.test.objects.SODPosition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The purpose of this class is to read the input SOD file
 * and generate a Map<Instrument,ArrayList<SODPosition>>
 * the purpose of creating a map is performance
 */
public class InputFileReader {

    private static final Logger LOGGER = Logger.getLogger("InputFileReader");

    private ArrayList<String> instrumentOrder = new ArrayList<>();

    /**
     * @param filePath - The path of the SOD file
     * @return a map of Instrument and a list of SOD positions
     */
    public Map<String, ArrayList<SODPosition>> getSodPosition(String filePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath)).skip(1)) {
            List<SODPosition> result = stream.map(x -> Arrays.asList(x.split(",")))
                    .filter(x -> validate(x))
                    .map(m -> new SODPosition(m.get(0), Integer.parseInt(m.get(1)), AccountType.accountTypeMap.get(m.get(2)), Integer.parseInt(m.get(3))))
                    .collect(Collectors.toList());
            return createOutputMap(result);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error parsing the file with path = " + filePath);
            throw e;
        }
    }

    public List<String> getInstrumentOrder() {
        return instrumentOrder;
    }

    /**
     * This method is responsible for generating the map from the list
     *
     * @param result - A list containing valid SODPositions
     * @return map of Instrument and a list of SOD positions
     */
    private Map<String, ArrayList<SODPosition>> createOutputMap(List<SODPosition> result) {
        Map<String, ArrayList<SODPosition>> position = new HashMap<>();
        for (SODPosition value : result) {
            if (position.containsKey(value.getInstrument())) {
                position.get(value.getInstrument()).add(value);
            } else {
                position.put(value.getInstrument(), Lists.newArrayList(value));
                instrumentOrder.add(value.getInstrument());
            }
        }
        return position;
    }


    /**
     * This method validates the inputs and identifies whether
     * the position in the file is valid. There can be more validatins added
     *
     * @param enteredLine - line read from the file
     * @return - whether the SOD input is valid
     */
    public boolean validate(List<String> enteredLine) {
        if (enteredLine.size() != 4) {
            LOGGER.log(Level.SEVERE, "Line = " + enteredLine.toString() + "does not have 4 values. It should have exactly 4 values ");
            return false;
        }
        if (AccountType.accountTypeMap.get(enteredLine.get(2)) == null) {
            LOGGER.log(Level.SEVERE, "Account Type = " + enteredLine.get(2) + " It should be either E or I for entereed line " + enteredLine.toString());
            return false;
        }
        return true;
    }

}
