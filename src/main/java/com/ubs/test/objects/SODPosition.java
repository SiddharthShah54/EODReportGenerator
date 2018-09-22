package com.ubs.test.objects;

public class SODPosition implements Comparable<SODPosition>{


    String instrument;
    Integer account;
    AccountType accountType;
    Integer quantity;


    public SODPosition(String instrument, Integer account, AccountType accountType, Integer quantity)
    {
        this.instrument = instrument;
        this.account = account;
        this.accountType = accountType;
        this.quantity = quantity;
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(SODPosition position) {
        if(this.getAccount().equals(position.getAccount())
                && this.getAccountType().equals(position.getAccountType())
            && this.getInstrument().equals(position.getInstrument()))
        {
            return 0;
        }
        return 1;
    }
}
