package com.panacare.panabeans.communication.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequestBody {

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("response_type")
    private String responseType;

    @JsonProperty("sender_name")
    private String senderName;

    @JsonProperty("service_id")
    private int serviceId;

    @JsonProperty("message")
    private String message;
}
