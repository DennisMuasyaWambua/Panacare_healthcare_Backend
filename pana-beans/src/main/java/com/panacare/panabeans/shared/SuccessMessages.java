package com.panacare.panabeans.shared;

public enum SuccessMessages {
    SUCCESS_PASSWORD_RESET("Password was reset successfully"),
    SUCCESS_RESEND_OTP("OTP was sent successfully"),

    TASK_COMPLETION_SUCCESS("Task was completed successfully"),

    TASK_REASSIGNMENT_SUCCESS("Task successfully Re-assigned"),

    SUCCESS_EMAIL_VERIFICATION("Email was successfully verified"),
    SUCCESS_USER_CREATED("User created. Check your email for verification link."),
    SUCCESS_DOCTOR_CREATED("Doctor created. Check your email for verification link."),
    SUCCESS_USER_DELETED("User was successfully deactivated"),
    SUCCESS_DELETED("Record was successfully deleted");

    private String successMessage;

    SuccessMessages(String successMessage){
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessages(String successMessage) {
        this.successMessage = successMessage;
    }
}
