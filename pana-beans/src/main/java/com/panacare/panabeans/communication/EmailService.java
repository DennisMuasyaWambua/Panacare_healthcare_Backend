package com.panacare.panabeans.communication;

import com.panacare.panabeans.dto.MailDto;

public interface EmailService {
    void sendCustomMail(MailDto mailDto, boolean mock);
}
