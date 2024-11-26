package org.example.authentication_service.service.user;

import jakarta.mail.MessagingException;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.handler.exception.EmailNotFoundException;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.model.entity.*;
import org.example.authentication_service.repository.UserRepository;
import org.example.authentication_service.service.confirm_token.ConfirmTokenService;
import org.example.authentication_service.service.email.EmailService;
import org.example.authentication_service.service.instance.InstanceService;
import org.example.authentication_service.service.password_token.PasswordResetTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private InstanceService instanceService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmTokenService confirmTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordResetTokenService passwordResetTokenService;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void loadUserByUsername() {
        String usernameAndInstance = "testUser:testInstance";
        String username = "testUser";
        String instanceName = "testInstance";

        Instance instance = new Instance();
        instance.setName(instanceName);

        User user = new User();
        user.setName(username);
        user.setPassword("encodedPassword");
        user.setRole(Role.ROLE_USER);
        user.setInstance(instance);

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(usernameAndInstance);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority())));
    }

    @Test
    void existsByEmailAndInstance() {
        String email = "test@example.com";
        String instanceName = "testInstance";
        Instance instance = new Instance();
        instance.setName(instanceName);

        User user = new User();
        user.setEmail(email);
        user.setInstance(instance);

        when(userRepository.findByMailAndInstance(email, instanceName)).thenReturn(Optional.of(user));

        boolean exists = userService.existsByEmailAndInstance(email, instanceName);

        assertTrue(exists);
    }

    @Test
    void doesnt_ExistsByEmailAndInstance() {
        String email = "nonexistent@example.com";
        String instanceName = "nonexistentInstance";

        when(userRepository.findByMailAndInstance(email, instanceName)).thenReturn(Optional.empty());

        boolean exists = userService.existsByEmailAndInstance(email, instanceName);

        assertFalse(exists);
    }


    @Test
    void createNewUser() throws MessagingException {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("testUser");
        registrationUserDto.setEmail("test@example.com");
        registrationUserDto.setPassword("password");
        registrationUserDto.setUserType(UserType.Edadil);

        Instance instance = new Instance();
        instance.setName("Edadil");

        User user = new User();
        user.setName("testUser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setInstance(instance);
        user.setRole(Role.ROLE_USER);
        user.setEnable(false);

        ConfirmationToken confirmationToken = new ConfirmationToken(UUID.randomUUID().toString(), user);

        when(instanceService.getByName("Edadil")).thenReturn(instance);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        doNothing().when(confirmTokenService).saveConfirmToken(any(ConfirmationToken.class));
        doNothing().when(emailService).sendEmailWithVerification(anyString(), anyString());

        userService.createNewUser(registrationUserDto);

        verify(instanceService, times(1)).getByName("Edadil");
        verify(passwordEncoder, times(1)).encode("password");
        verify(confirmTokenService, times(1)).saveConfirmToken(any(ConfirmationToken.class));
        verify(emailService, times(1)).sendEmailWithVerification(eq("test@example.com"), anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void existsByUsernameAndInstance() {
        String username = "testUser";
        String instanceName = "testInstance";
        Instance instance = new Instance();
        instance.setName(instanceName);

        User user = new User();
        user.setName(username);
        user.setInstance(instance);

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.of(user));

        boolean exists = userService.existsByUsernameAndInstance(username, instanceName);

        assertTrue(exists);
    }

    @Test
    void doesnt_ExistsByUsernameAndInstance() {
        String username = "nonexistentUser";
        String instanceName = "nonexistentInstance";

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.empty());

        boolean exists = userService.existsByUsernameAndInstance(username, instanceName);

        assertFalse(exists);
    }

    @Test
    void isEmailConfirmed() {
        String confirmationToken = UUID.randomUUID().toString();
        User user = new User();
        user.setEnable(false);

        ConfirmationToken token = new ConfirmationToken(confirmationToken, user);

        when(confirmTokenService.findByConfirmationToken(confirmationToken)).thenReturn(token);
        when(userRepository.save(user)).thenReturn(user);

        boolean result = userService.isEmailConfirmed(confirmationToken);

        assertTrue(result);
        assertTrue(user.isEnable());
    }

    @Test
    void isEmailConfirmed_alreadyEnabled() {
        String confirmationToken = UUID.randomUUID().toString();
        User user = new User();
        user.setEnable(true);

        ConfirmationToken token = new ConfirmationToken(confirmationToken, user);

        when(confirmTokenService.findByConfirmationToken(confirmationToken)).thenReturn(token);

        boolean result = userService.isEmailConfirmed(confirmationToken);

        assertFalse(result);
    }

    @Test
    void isVerified_userEnabled() {
        String username = "testUser";
        String instanceName = "testInstance";
        Instance instance = new Instance();
        instance.setName(instanceName);

        User user = new User();
        user.setName(username);
        user.setInstance(instance);
        user.setEnable(true);

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.of(user));

        boolean isVerified = userService.isVerified(username, instanceName);

        assertTrue(isVerified);
    }

    @Test
    void isVerified_userNotEnabled() {
        String username = "testUser";
        String instanceName = "testInstance";
        Instance instance = new Instance();
        instance.setName(instanceName);

        User user = new User();
        user.setName(username);
        user.setInstance(instance);
        user.setEnable(false);

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.of(user));

        boolean isVerified = userService.isVerified(username, instanceName);

        assertFalse(isVerified);
    }

    @Test
    void updatePassword() {
        User user = new User();
        user.setPasswordResetToken(new PasswordResetToken());

        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        userService.updatePassword(user, "newPassword");

        assertTrue(user.getPasswordResetToken().getUsed());
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void resetPassword() throws MessagingException {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("test@example.com");
        request.setUserType(UserType.Edadil.toString());

        User user = new User();
        user.setEnable(true);

        when(userRepository.findByMailAndInstance("test@example.com", "Edadil")).thenReturn(Optional.of(user));
        doNothing().when(emailService).sendEmailWithRestorePassword(anyString(), anyString());

        userService.resetPassword(request);

        verify(userRepository, times(1)).findByMailAndInstance("test@example.com", "Edadil");
        verify(emailService, times(1)).sendEmailWithRestorePassword(eq("test@example.com"), anyString());
    }

    @Test
    void findUserByNameAndInstance() {
        String username = "testUser";
        String instanceName = "testInstance";
        User user = new User();

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByNameAndInstance(username, instanceName);

        assertEquals(user, foundUser);
    }

    @Test
    void findByMailAndInstance() {
        String email = "test@example.com";
        String instanceName = "testInstance";
        User user = new User();

        when(userRepository.findByMailAndInstance(email, instanceName)).thenReturn(Optional.of(user));

        User foundUser = userService.findByMailAndInstance(email, instanceName);

        assertEquals(user, foundUser);
    }


    @Test
    void findUserByNameAndInstance_userNotFound() {
        String username = "nonexistentUser";
        String instanceName = "nonexistentInstance";

        when(userRepository.findByUsernameAndInstance(username, instanceName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByNameAndInstance(username, instanceName));
    }

    @Test
    void findByMailAndInstance_userNotFound() {
        String email = "nonexistent@example.com";
        String instanceName = "nonexistentInstance";

        when(userRepository.findByMailAndInstance(email, instanceName)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> userService.findByMailAndInstance(email, instanceName));
    }

    @Test
    void findUserByPasswordToken() {
        String token = "resetToken";
        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);

        when(userRepository.findUserByPasswordResetToken(token)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByPasswordToken(token);

        assertEquals(user, foundUser);
    }

    @Test
    void findUserByPasswordToken_userNotFound() {
        String token = "invalidToken";

        when(userRepository.findUserByPasswordResetToken(token)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByPasswordToken(token));
    }
}