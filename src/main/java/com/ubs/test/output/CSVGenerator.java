
package com.ubs.test.output;

import com.ubs.test.objects.EODPosition;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVGenerator {

    public static final Logger LOGGER = Logger.getLogger("CSVGenerator");
    CSVFormat format = CSVFormat.DEFAULT.withHeader("Instrument", "Account", "AccountType", "Quantity","Delta").withDelimiter(',');

    /**
     * Generate the CSV file at the desire location. Currently it
     * is generate under resources folder with name EndOfDay_Positions.txt
     * @param positions - Map of instrument versus List of EOD position
     * @param instrumentOrder - original order of Instrument in SOD file
     * @param filePath
     */
    public void generateCSV(Map<String, ArrayList<EODPosition>> positions, List<String> instrumentOrder, String filePath) throws IOException {
        FileWriter fileWriter;
        CSVPrinter printer = null;

        Comparator<EODPosition> eodPositionComparator = Comparator.comparing(EODPosition :: getAccount)
        .thenComparing(EODPosition :: getAccountType);

        try {
            fileWriter = new FileWriter(Paths.get(filePath).toAbsolutePath().toString());
            printer = new CSVPrinter(fileWriter, format);
            for(String instrument : instrumentOrder) {
                positions.get(instrument).sort(eodPositionComparator);
                for (EODPosition eodPosition : positions.get(instrument)) {
                    List<String> eodData = new ArrayList<>();
                    eodData.add(eodPosition.getInstrument());
                    eodData.add(eodPosition.getAccount().toString());
                    eodData.add(eodPosition.getAccountType().name());
                    eodData.add(eodPosition.getQuantity().toString());
                    eodData.add(eodPosition.getDelta().toString());
                    printer.printRecord(eodData);
                }
            }
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Error while saving the EOD file to disk");
            throw e;
        }
        finally {
            try {
                printer.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,"Error while closing the instance of Printer");
            }
        }

    }

}
