package com.ubs.test.objects;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("TransactionId")
    Integer transactionId;
    @SerializedName("Instrument")
    String instrument;
    @SerializedName("TransactionType")
    TransactionType transcationType;
    @SerializedName("TransactionQuantity")
    Integer quantity;

    public Transaction(Integer transactionId, String instrument, TransactionType transcationType, Integer quantity) {
        this.transactionId = transactionId;
        this.instrument = instrument;
        this.transcationType = transcationType;
        this.quantity = quantity;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public String getInstrument() {
        return instrument;
    }

    public TransactionType getTranscationType() {
        return transcationType;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
