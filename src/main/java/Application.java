import com.ubs.test.input.InputFileReader;
import com.ubs.test.input.JsonFileReader;
import com.ubs.test.objects.EODPosition;
import com.ubs.test.objects.SODPosition;
import com.ubs.test.objects.Transaction;
import com.ubs.test.output.CSVGenerator;
import com.ubs.test.processor.DataProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Application {
    private static final Properties properties = new Properties();
    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
//        System.setProperty("log4j.configurationFile","src/main/resources/log4j2.xml");
        loadProperties();
        InputFileReader reader  = new InputFileReader();
        Map<String, ArrayList<SODPosition>> sodPosition = reader.getSodPosition(properties.getProperty("SODFilePath"));
        LOGGER.debug("Completed reading of input SOD file");
        JsonFileReader jsonFileReader = new JsonFileReader();
        List<Transaction> transactions = jsonFileReader.getTransactions(properties.getProperty("transactionFilePath"));
        LOGGER.debug("Completed reading of json transaction file");
        DataProcessor processor = new DataProcessor(sodPosition,transactions);
        Map<String,ArrayList<EODPosition>> output = processor.process();
        LOGGER.debug("Completed creating output");
        CSVGenerator generator = new CSVGenerator();
        generator.generateCSV(output,reader.getInstrumentOrder(),properties.getProperty("EODFilePath"));
        LOGGER.debug("Completed creation of output file");
    }

    private static void loadProperties() throws IOException {
        InputStream input = new FileInputStream(Paths.get("src/main/config/config.properties").toAbsolutePath().toString());
        properties.load(input);
    }
}
