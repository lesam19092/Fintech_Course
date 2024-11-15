package org.example.authentication_service.model.consts;

public interface Email {

    String SUBJECT = "Complete Registration!";

    String MESSAGE = "To confirm your account, please click here : "
            + "http://localhost:1010/auth/confirm-account?confirmationToken=";


}
