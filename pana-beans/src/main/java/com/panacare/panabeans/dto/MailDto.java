package com.panacare.panabeans.dto;


import lombok.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String from;
    private String to;
    private HtmlTemplate htmlTemplate;
    private String subject;
    private List<File> files;
    private String[] cc;
    private ClassPathResource companyLogo;
    private ClassPathResource processImage;
}
