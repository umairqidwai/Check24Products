package com.example.check24products.data.model;

import java.io.Serializable;

public class Price implements Serializable {

    private double value;
    private String currency;



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
