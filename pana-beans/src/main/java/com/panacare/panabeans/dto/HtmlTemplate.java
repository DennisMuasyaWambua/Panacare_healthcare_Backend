package com.panacare.panabeans.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HtmlTemplate {

    private String template;

    private Map<String, Object> props;

    public HtmlTemplate(String template, Map<String, Object> props) {
        this.template = template;
        this.props = props;
    }
}
