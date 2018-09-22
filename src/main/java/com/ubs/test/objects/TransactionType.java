package com.ubs.test.objects;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
    @SerializedName("B")
    B,
    @SerializedName("S")
    S;

    public static final Map<String,TransactionType> transactionTypeMap = new HashMap<>();

    static
    {
        for(TransactionType type:TransactionType.values())
        transactionTypeMap.put(type.name(),type);
    }
}
