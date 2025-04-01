package com.panacare.panabeans.workflow.utilities;

import com.panacare.panabeans.communication.EmailService;
import com.panacare.panabeans.communication.SMSSenderService;
import com.panacare.panabeans.data.entity.UserEntity;
import com.panacare.panabeans.dto.HtmlTemplate;
import com.panacare.panabeans.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ActivityUtils {

    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.from.email}")
    private  String fromEmail;

    @Value("${app.support.email}")
    private  String supportEmail;

    @Value("${app.support.line}")
    private  String supportPhoneNo;

    private final EmailService emailService;

    private final SMSSenderService smsSenderService;


    public void sendEmailNotification(UserEntity recipient, String[] cc, String subject , String message, String template,
                                     ClassPathResource processImg, List<File> files, String url) {
        MailDto mailData = getMailData(recipient, template, subject, message, files, cc, processImg, url);
        emailService.sendCustomMail(mailData, Boolean.FALSE);
    }

    public void sendSmsNotification(UserEntity user, String message) {
        smsSenderService.sendSMS(user.getPhoneNumber(), message);
    }



    private MailDto getMailData(UserEntity recipient, String template, String subject, String message,
                                List<File> files, String[] cc, ClassPathResource processImg, String url) {
        String recipientName = recipient.getFirstName() + " " + recipient.getLastName();
        ClassPathResource orgLogo = new ClassPathResource("static/modules/workforce/img/panacare_logo.png");
//        Get  Email template
        Map<String, Object> props = new HashMap<>();
        props.put("org", "PanaCare");
        props.put("year", LocalDate.now().getYear());
        props.put("project", "PanaCare Portal");
        props.put("message", message);
        props.put("recipientName", recipientName);
        props.put("recipientEmail", recipient.getEmail());
        props.put("url", url);
        props.put("basepath", baseUrl);
        props.put("supportEmail", supportEmail);
        props.put("supportPhone", supportPhoneNo);

        HtmlTemplate htmlTemplate = new HtmlTemplate(template, props);

        return MailDto.builder()
                .from(fromEmail)
                .to(recipient.getEmail())
                .cc(Optional.ofNullable(cc).isPresent() ? cc : null)
                .subject(subject)
                .htmlTemplate(htmlTemplate)
                .companyLogo(orgLogo)
                .processImage(processImg)
                .files(Optional.ofNullable(files).isPresent()? files : null)
                .build();
    }
}
