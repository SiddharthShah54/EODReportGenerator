import com.ubs.test.input.InputFileReader;
import com.ubs.test.input.JsonFileReader;
import com.ubs.test.objects.EODPosition;
import com.ubs.test.objects.SODPosition;
import com.ubs.test.objects.Transaction;
import com.ubs.test.output.CSVGenerator;
import com.ubs.test.processor.DataProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Application {
    public static final Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        loadProperties();
        InputFileReader reader  = new InputFileReader();
        Map<String, ArrayList<SODPosition>> sodPosition = reader.getSodPosition(properties.getProperty("SODFilePath"));
        JsonFileReader jsonFileReader = new JsonFileReader();
        List<Transaction> transactions = jsonFileReader.getTransactions(properties.getProperty("transactionFilePath"));
        DataProcessor processor = new DataProcessor(sodPosition,transactions);
        Map<String,ArrayList<EODPosition>> output = processor.process();
        CSVGenerator generator = new CSVGenerator();
        generator.generateCSV(output,reader.getInstrumentOrder(),properties.getProperty("EODFilePath"));
    }

    private static void loadProperties() throws IOException {
        InputStream input = new FileInputStream(Paths.get("src/main/config/config.properties").toAbsolutePath().toString());
        properties.load(input);
    }
}
