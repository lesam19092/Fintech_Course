package org.example.authentication_service.model.consts;

public interface Email {

    String COMPLETE_REGISTRATION = "Complete Registration!";

    String CONFIRM_MESSAGE = "To confirm your account, please click here : "
            + "http://localhost:1010/auth/confirm-account?confirmationToken=";

    String RESTORE_PASSWORD = "Restore Password!";

    String CONFIRM_MESSAGE_RESTORE = "To restore your password, please click here : "
            + "http://localhost:1010/auth/restore-password?passwordToken=";

}
