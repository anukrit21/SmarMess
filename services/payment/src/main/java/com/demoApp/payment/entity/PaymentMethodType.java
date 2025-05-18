package com.demoApp.payment.entity;

public enum PaymentMethodType {
    CREDIT_CARD("card"),
    DEBIT_CARD("card"),
    BANK_TRANSFER("bank_transfer"),
    PAYPAL("paypal"),
    WALLET("wallet"),
    UPI("upi"),
    CASH_ON_DELIVERY("cash"),
    GOOGLE_PAY("google_pay"),
    PHONE_PE("phone_pe"),
    PAYTM("paytm"),
    BHIM("bhim"),
    AMAZON_PAY("amazon_pay"),
    HDFC("hdfc"),
    SBI("sbi"),
    ICICI("icici"),
    AXIS("axis");

    private final String stripeMethod;

    PaymentMethodType(String stripeMethod) {
        this.stripeMethod = stripeMethod;
    }

    public String getStripeMethod() {
        return stripeMethod;
    }

    public String getSubtype() {
        return name().toLowerCase();
    }
} 