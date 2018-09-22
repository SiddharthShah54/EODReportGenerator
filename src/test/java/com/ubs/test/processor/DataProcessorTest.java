package com.ubs.test.processor;

import com.google.common.collect.Lists;
import com.ubs.test.objects.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DataProcessorTest {

    SODPosition sODPositionIbm1 = new SODPosition("IBM",101, AccountType.E,1000000);
    SODPosition sODPositionIbm2 = new SODPosition("IBM",201, AccountType.I,-1000000);
    SODPosition sODPositionAppl1 = new SODPosition("APPL",101, AccountType.E,10000);
    SODPosition sODPositionAppl2 = new SODPosition("APPL",201, AccountType.I,-10000);

    Transaction transactionBuy1 = new Transaction(1,"IBM", TransactionType.B,1000);
    Transaction transactionBuy2 = new Transaction(2,"APPL", TransactionType.B,1000);

    Transaction transactionSell1 = new Transaction(1,"IBM", TransactionType.S,1000);
    Transaction transactionSell2 = new Transaction(2,"APPL", TransactionType.S,1000);

    @Test
    public void processWithBuyForAccountTypeE() throws Exception {
        ArrayList<SODPosition> positionsIbm = Lists.newArrayList(sODPositionIbm1);
        ArrayList<SODPosition> positionsAppl = Lists.newArrayList(sODPositionAppl1);
        Map<String,ArrayList<SODPosition>> positions  =new HashMap<>();
        positions.put("IBM",positionsIbm);
        positions.put("APPL",positionsAppl);
        List<Transaction> transactions = Arrays.asList(transactionBuy1, transactionBuy2);
        DataProcessor processor = new DataProcessor(positions,transactions);
        Map<String, ArrayList<EODPosition>> eodPosition = processor.process();
        ArrayList<EODPosition> ibmPositions = eodPosition.get("IBM");
        Assert.assertEquals(ibmPositions.get(0).getQuantity(),new Integer(1001000));
        Assert.assertEquals(ibmPositions.get(0).getDelta(),new Integer(1000));
        ArrayList<EODPosition> applPositions = eodPosition.get("APPL");
        Assert.assertEquals(applPositions.get(0).getQuantity(),new Integer(11000));
        Assert.assertEquals(applPositions.get(0).getDelta(),new Integer(1000));
    }

    @Test
    public void processWithBuyForAccountTypeI() throws Exception {
        ArrayList<SODPosition> positionsIbm = Lists.newArrayList(sODPositionIbm2);
        ArrayList<SODPosition> positionsAppl = Lists.newArrayList(sODPositionAppl2);
        Map<String,ArrayList<SODPosition>> positions  =new HashMap<>();
        positions.put("IBM",positionsIbm);
        positions.put("APPL",positionsAppl);
        List<Transaction> transactions = Arrays.asList(transactionBuy1, transactionBuy2);
        DataProcessor processor = new DataProcessor(positions,transactions);
        Map<String, ArrayList<EODPosition>> eodPosition = processor.process();
        ArrayList<EODPosition> ibmPositions = eodPosition.get("IBM");
        Assert.assertEquals(ibmPositions.get(0).getQuantity(),new Integer(-1001000));
        Assert.assertEquals(ibmPositions.get(0).getDelta(),new Integer(-1000));
        ArrayList<EODPosition> applPositions = eodPosition.get("APPL");
        Assert.assertEquals(applPositions.get(0).getQuantity(),new Integer(-11000));
        Assert.assertEquals(applPositions.get(0).getDelta(),new Integer(-1000));
    }

    @Test
    public void processWithSellForAccountTypeE() throws Exception {
        ArrayList<SODPosition> positionsIbm = Lists.newArrayList(sODPositionIbm1);
        ArrayList<SODPosition> positionsAppl = Lists.newArrayList(sODPositionAppl1);
        Map<String,ArrayList<SODPosition>> positions  =new HashMap<>();
        positions.put("IBM",positionsIbm);
        positions.put("APPL",positionsAppl);
        List<Transaction> transactions = Arrays.asList(transactionSell1, transactionSell2);
        DataProcessor processor = new DataProcessor(positions,transactions);
        Map<String, ArrayList<EODPosition>> eodPosition = processor.process();
        ArrayList<EODPosition> ibmPositions = eodPosition.get("IBM");
        Assert.assertEquals(ibmPositions.get(0).getQuantity(),new Integer(999000));
        Assert.assertEquals(ibmPositions.get(0).getDelta(),new Integer(-1000));
        ArrayList<EODPosition> applPositions = eodPosition.get("APPL");
        Assert.assertEquals(applPositions.get(0).getQuantity(),new Integer(9000));
        Assert.assertEquals(applPositions.get(0).getDelta(),new Integer(-1000));
    }

    @Test
    public void processWithSellForAccountTypeI() throws Exception {
        ArrayList<SODPosition> positionsIbm = Lists.newArrayList(sODPositionIbm2);
        ArrayList<SODPosition> positionsAppl = Lists.newArrayList(sODPositionAppl2);
        Map<String,ArrayList<SODPosition>> positions  =new HashMap<>();
        positions.put("IBM",positionsIbm);
        positions.put("APPL",positionsAppl);
        List<Transaction> transactions = Arrays.asList(transactionSell1, transactionSell2);
        DataProcessor processor = new DataProcessor(positions,transactions);
        Map<String, ArrayList<EODPosition>> eodPosition = processor.process();
        ArrayList<EODPosition> ibmPositions = eodPosition.get("IBM");
        Assert.assertEquals(ibmPositions.get(0).getQuantity(),new Integer(-999000));
        Assert.assertEquals(ibmPositions.get(0).getDelta(),new Integer(1000));
        ArrayList<EODPosition> applPositions = eodPosition.get("APPL");
        Assert.assertEquals(applPositions.get(0).getQuantity(),new Integer(-9000));
        Assert.assertEquals(applPositions.get(0).getDelta(),new Integer(1000));
    }

}