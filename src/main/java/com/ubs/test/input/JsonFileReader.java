
package com.ubs.test.input;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ubs.test.objects.Transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonFileReader {
    public static Logger LOGGER = Logger.getLogger("JsonFileReader");

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
            LOGGER.log(Level.SEVERE,"Could not find the file specified for path = " + filePath);
            throw e;
        }
        Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType();
        return new GsonBuilder().serializeNulls().create().fromJson(reader, listType);
    }
}
