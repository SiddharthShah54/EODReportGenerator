
package com.ubs.test.input;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ubs.test.objects.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {
    private static final Logger LOGGER = LogManager.getLogger(JsonFileReader.class);

    /**
     * This method reads the Json file containing transactions
     * @param filePath - Transaction file path
     * @return - List of Transaction Object
     * @throws FileNotFoundException
     */
    public List<Transaction> getTransactions(String filePath) throws FileNotFoundException {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(Paths.get(filePath).toAbsolutePath().toString()));
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find the file specified for path = " + filePath);
            throw e;
        }
        Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType();
        return new GsonBuilder().serializeNulls().create().fromJson(reader, listType);
    }
}
