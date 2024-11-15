package org.example.authentication_service.service.check;

import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;

public interface CheckService {

    void checkUser(RegistrationUserDto registrationUserDto);


    void checkVerification(LoginUserDto loginUserDto);

}