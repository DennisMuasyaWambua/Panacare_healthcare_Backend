package com.panacare.panabeans.shared;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field. Please checkHoliday documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    RECORD_NOT_FOUND("Record with provided id not found"),
    RECORDS_NOT_FOUND("No records found or data not available."),
    NO_ACTIVE_RECORD_FOUND("No Active Record with provided id found"),
    RECORD_MISMATCH("Supplied Record Doesn't Match with the record to be updated!"),
    FILE_NOT_FOUND("Requested File Not Found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    OTP_VERIFICATION_FAILED("OTP verification failed"),
    EXPIRED_SESSION("Session Expired, Login top proceed"),
    INVALID_CREDENTIALS("Incorrect Email/Password Provided!"),
    NOT_AUTHORIZED("User is not authorized. Log in to proceed!"),
    ACCOUNT_LOCKED("Account is blocked!"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    ERROR_PASSWORD_RESET("Failed to Reset Password. Please try again!"),
    ERROR_RESEND_OTP("Failed to resend OTP. Please try again!"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    EMAIL_ADDRESS_NOT_FOUND("Email address could not be Found! :"),
    FAILED_USER_CREATION("Could not create user due to an error: ");

    private String errorMessage;

    ErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
