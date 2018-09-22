package com.ubs.test.objects;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {
    E,I;

    public static Map<String,AccountType> accountTypeMap = new HashMap<>();

    static
    {
        for(AccountType type:AccountType.values())
        accountTypeMap.put(type.name(),type);
    }

}
