package com.laptrinhweb.entity;

import lombok.Data;


public enum PaymentMethod {
    Credit_Card("Credit Card"), PayPal("PayPal"), Momo("Momo"), Other("Momo");
    private String value;

    PaymentMethod(String _value){
        value=_value;
    }
    public String getName()
    {
        return this.value;
    }


}