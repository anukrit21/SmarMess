package com.demoApp.delivery.service;

import org.springframework.stereotype.Component;

@Component
public class SmsSender {

    public void sendSms(String phoneNumber, String message) {
        // Simulate sending SMS
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        
        // In real scenarios, integrate with Twilio or SMS gateway
        // Twilio example:
        // Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("YOUR_TWILIO_NUMBER"), message).create();
    }
}
