package com.panacare.panabeans.shared;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppProperties {
    public static final DateFormat systemDF = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    public static final long EXPIRATION_TIME = 864000000; //10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1 HOUR
    public static final int WORKFLOW_VALIDITY_PERIOD = 3; //3 DAYS
    public static final String APP_TOKEN_SECRET = "jf9i4jgu83nfl0";
    private static final String APP_SECRET_KEY = "dms-secret-key";
    private final Environment env;


    public int getWorkflowValidityDays(){
        return StringUtils.isEmpty(env.getProperty("workflowExpiryDays")) ? WORKFLOW_VALIDITY_PERIOD :
                Integer.parseInt(Objects.requireNonNull(env.getProperty("workflowExpiryDays")));
    }

    public long getEmailExpirationTime() {
        return EXPIRATION_TIME;
    }
}
