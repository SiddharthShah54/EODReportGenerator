package com.ubs.test.objects;

public class EODPosition{


    String instrument;
    Integer account;
    AccountType accountType;
    Integer quantity;
    Integer delta;

    public EODPosition(String instrument, Integer account, AccountType accountType, Integer quantity, Integer delta) {
        this.instrument = instrument;
        this.account = account;
        this.accountType = accountType;
        this.quantity = quantity;
        this.delta = delta;
    }

    public String getInstrument() {
        return instrument;
    }

    public Integer getAccount() {
        return account;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getDelta() {
        return delta;
    }


}
