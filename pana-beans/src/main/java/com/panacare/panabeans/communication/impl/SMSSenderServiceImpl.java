package com.panacare.panabeans.communication.impl;


import com.google.gson.Gson;
import com.panacare.panabeans.communication.SMSSenderService;
import com.panacare.panabeans.communication.model.request.SmsRequestBody;
import com.panacare.panabeans.exception.PanaAppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SMSSenderServiceImpl implements SMSSenderService {


    @Value("${app.sms.sender-url}")
    private String SEND_SMS_ENDPOINT;

    @Value("${app.sms.sender-name}")
    private String SEND_SMS_SENDER_TAG;

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async
    public void sendSMS(String phoneNumber, String messageBody) {
        SmsRequestBody sms = SmsRequestBody
                .builder()
                .mobile(phoneNumber)
                .responseType("json")
                .senderName(SEND_SMS_SENDER_TAG)
                .serviceId(0)
                .message(messageBody)
                .build();

        logger.info("sending the sms {}", sms);
        sendSMS(sms);
    }

    private void sendSMS(SmsRequestBody sms) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("h_api_key", "5c222d7f91caf0d658ab02f1dee06a492e13eb0a3deef200c3f1d30579de1aec");
        HttpEntity<SmsRequestBody> request = new HttpEntity<>(sms, headers);
        logger.info("RestTemplate Request {} ", new Gson().toJson(request.getBody()));

        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(
                    SEND_SMS_ENDPOINT,
                    request, Object.class
            );

            logger.info("RestTemplate Response {} ", new Gson().toJson(response));
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = new Gson().toJson(response.getBody());
                logger.info("SmsRevamp Response Body Object {} ", responseBody);
            } else {
                throw new PanaAppException(Objects.requireNonNull(response.getBody()).toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String error = String.format("an error occurred %s details", ex.getMessage());
            logger.error(error);
            throw new PanaAppException(error);
        }

    }
}
