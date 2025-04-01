package com.panacare.panabeans.communication;


public interface SMSSenderService {
    void sendSMS(String phoneNumber, String messageBody);

}
