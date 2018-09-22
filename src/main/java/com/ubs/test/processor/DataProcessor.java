package com.ubs.test.processor;

import com.google.common.collect.Lists;
import com.ubs.test.objects.*;

import java.util.*;
import java.util.logging.Logger;

public class DataProcessor {
    public static final Logger LOGGER = Logger.getLogger("DataProcessor");
    Map<String, ArrayList<SODPosition>> sodPositions;
    List<Transaction> transactions;

    public DataProcessor(Map<String, ArrayList<SODPosition>> sodPositions, List<Transaction> transactions) {
        this.sodPositions = sodPositions;
        this.transactions = transactions;
    }


    /**
     * Generates EOD positions
     * @return
     */
    public Map<String, ArrayList<EODPosition>> process() {
        Map<SODPosition, Integer> sodPositionsWithDelta = updateSODPositions();
        return generateOutput(sodPositionsWithDelta);
    }

    /**
     * Generates EODPosition with Delta values
     * @param sodPositionsWithDelta
     * @return Map of Instrument versus list of EOD positions
     */
    private Map<String, ArrayList<EODPosition>> generateOutput(Map<SODPosition, Integer> sodPositionsWithDelta) {
        Map<String, ArrayList<EODPosition>> output = new HashMap<>();
        for (Map.Entry<SODPosition, Integer> entry : sodPositionsWithDelta.entrySet()) {
            SODPosition position = entry.getKey();
            if (output.containsKey(position.getInstrument())) {
                output.get(position.getInstrument()).add(new EODPosition(position.getInstrument(), position.getAccount(), position.getAccountType(), position.getQuantity(), entry.getValue()));
            } else {
                output.put(position.getInstrument(), Lists.newArrayList(new EODPosition(position.getInstrument(), position.getAccount(), position.getAccountType(), position.getQuantity(), entry.getValue())));
            }

        }
        return output;
    }

    /**
     * Class which updates the SOD positions by adding transactions
     * to generate EOD Position
     * @return
     */
    private Map<SODPosition, Integer> updateSODPositions() {
        Map<SODPosition, Integer> sodPositionsWithDelta = new HashMap<>();
        Set<String> instrumentsProcessed = new HashSet<>();
        for (Transaction transaction : transactions) {
            List<SODPosition> positions = sodPositions.get(transaction.getInstrument());
            instrumentsProcessed.add(transaction.getInstrument());
            if (TransactionType.B.equals(transaction.getTranscationType())) {
                for (SODPosition position : positions) {
                    if (AccountType.E.equals(position.getAccountType())) {
                        position.setQuantity(position.getQuantity() + transaction.getQuantity());
                        sodPositionsWithDelta = populateDeltaMap(sodPositionsWithDelta, position, transaction.getQuantity());
                    } else {
                        position.setQuantity(position.getQuantity() - transaction.getQuantity());
                        sodPositionsWithDelta = populateDeltaMap(sodPositionsWithDelta, position, (transaction.getQuantity() * -1));
                    }
                }
            } else {
                for (SODPosition position : positions) {
                    if (AccountType.E.equals(position.getAccountType())) {
                        position.setQuantity(position.getQuantity() - transaction.getQuantity());
                        sodPositionsWithDelta = populateDeltaMap(sodPositionsWithDelta, position, (transaction.getQuantity() * -1));
                    } else {
                        position.setQuantity(position.getQuantity() + transaction.getQuantity());
                        sodPositionsWithDelta = populateDeltaMap(sodPositionsWithDelta, position, transaction.getQuantity());
                    }
                }
            }
        }

        for (Map.Entry<String, ArrayList<SODPosition>> entry : sodPositions.entrySet()) {
            if (!instrumentsProcessed.contains(entry.getKey())) {
                for (SODPosition value : entry.getValue()) {
                    sodPositionsWithDelta.put(value, 0);
                }
            }
        }
        return sodPositionsWithDelta;
    }

    /**
     * @param sodPositionsWithDelta - Map containing SODPosition with Delta value
     * @param position - SOD Position
     * @param quantity - Transaction Quantity
     * @return updated sodPositionsWithDelta
     */
    private Map<SODPosition, Integer> populateDeltaMap(Map<SODPosition, Integer> sodPositionsWithDelta, SODPosition position, Integer quantity) {
        if (sodPositionsWithDelta.containsKey(position)) {
            sodPositionsWithDelta.put(position, sodPositionsWithDelta.get(position) + quantity);
        } else {
            sodPositionsWithDelta.put(position, quantity);
        }
        return sodPositionsWithDelta;
    }

}
