package com.ubs.test.input;

import com.ubs.test.objects.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class JsonFileReaderTest {
    @Test(expected = FileNotFoundException.class)
    public void getTransactionsWithFileAbsent() throws Exception {
        JsonFileReader reader = new JsonFileReader();
        reader.getTransactions("src/test/resorces/1.txt");
    }

    @Test
    public void getTransactionsWithEmptyJson() throws Exception {
        JsonFileReader reader = new JsonFileReader();
        reader.getTransactions("src/test/resources/SOD-Empty.txt");
    }

    @Test
    public void getTransactionsWithEmptyArray() throws Exception {
        JsonFileReader reader = new JsonFileReader();
        List<Transaction> transactions = reader.getTransactions("src/test/resources/Transaction-Empty.txt");
        Assert.assertEquals(transactions.size(),0);
    }

    @Test
    public void getTransactionsWithValidTransactions() throws Exception {
        JsonFileReader reader = new JsonFileReader();
        List<Transaction> transactions = reader.getTransactions("src/test/resources/Transaction-Valid.txt");
        Assert.assertEquals(transactions.size(),12);
    }

}