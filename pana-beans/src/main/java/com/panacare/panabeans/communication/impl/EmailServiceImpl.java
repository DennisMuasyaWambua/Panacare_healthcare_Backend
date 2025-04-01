package com.panacare.panabeans.communication.impl;


import com.panacare.panabeans.communication.EmailService;
import com.panacare.panabeans.dto.HtmlTemplate;
import com.panacare.panabeans.dto.MailDto;
import com.panacare.panabeans.exception.PanaAppException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

//    @Value("${smpt-from}")
//    private String SMPT_FROM;
//    @Value("${smpt-bcc}")
//    private String EMAIL_BCC;
//    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sendCustomMail(MailDto mailDto, boolean mock) {
        try {
            String htmlMailBody = getHtmlContent(mailDto.getHtmlTemplate());

            if (mock) {
                logger.info("sending email {} with subject {} to {}", htmlMailBody, mailDto.getSubject(), mailDto.getTo());
            } else {
//                MimeMessage msg = javaMailSender.createMimeMessage();
//                MimeMessageHelper helper = new MimeMessageHelper(msg,
//                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                        StandardCharsets.UTF_8.name());
//
//                helper.setTo(mailDto.getTo());
//                helper.setFrom(SMPT_FROM);
//                helper.setCc(mailDto.getCc());
//                helper.setBcc(EMAIL_BCC);
//                helper.setSubject(mailDto.getSubject());
//                helper.setText(htmlMailBody, true);
//
//                if(Optional.ofNullable(mailDto.getFiles()).isPresent() && mailDto.getFiles().size() > 0 ){
//                    for(File f:mailDto.getFiles()){
//                        FileSystemResource file = new FileSystemResource(f);
//                        helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
//                    }
//                }
//                if(Optional.ofNullable(mailDto.getCc()).isPresent() && mailDto.getCc().length > 0 )
//                    helper.setCc(mailDto.getCc());
                logger.info("Starting email Sending to: {} for: {}.....", mailDto.getTo(),mailDto.getSubject());
//                javaMailSender.send(msg);
                logger.info("Done Sending email to: {} for: {}.....", mailDto.getTo(),mailDto.getSubject());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String errorMsg = String.format("error when sending email %s", ex.getMessage());
            throw new PanaAppException(errorMsg);
        }
    }
    private String getHtmlContent(HtmlTemplate htmlTemplate) {
        Context context = new Context();
        context.setVariables(htmlTemplate.getProps());
        return templateEngine.process(htmlTemplate.getTemplate(), context);
    }


}
