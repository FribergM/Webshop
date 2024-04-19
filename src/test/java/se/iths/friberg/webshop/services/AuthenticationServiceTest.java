package se.iths.friberg.webshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import se.iths.friberg.webshop.db.entities.User;
import se.iths.friberg.webshop.db.repositories.UserRepository;
import se.iths.friberg.webshop.session.SessionManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest{

    @Mock
    UserRepository mockRepo;
    @Spy
    SessionManager mockSessionManager;
    @InjectMocks
    AuthenticationService mockAuthService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateLogin(){
        String username = "Test";
        when(mockRepo.findByUsername(username)).thenReturn(new User("Test","TestPw"));


        mockAuthService.validateLogin(username,"WrongPw");
        assertNull(mockSessionManager.getUser());
        mockAuthService.validateLogin(username,"TestPw");
        assertNotNull(mockSessionManager.getUser());
    }

    @Test
    void validateRegistration(){
        String validUsername = "TestUsername";
        String validPassword = "TestPw";
        String invalidUsername = "asd";
        String invalidPassword = "asd";

        when(mockRepo.existsByUsername(validUsername)).thenReturn(true);
        String result = mockAuthService.validateRegistration(validUsername, validPassword, validPassword);
        assertEquals("Username is already taken.",result);

        when(mockRepo.existsByUsername(validUsername)).thenReturn(false);

        result = mockAuthService.validateRegistration(validUsername, validPassword, validPassword);
        assertEquals("",result);

        result = mockAuthService.validateRegistration(invalidUsername, validPassword, validPassword);
        assertEquals("Invalid username",result);

        result = mockAuthService.validateRegistration(validUsername, invalidPassword,invalidPassword);
        assertEquals("Invalid password",result);

        result = mockAuthService.validateRegistration(validUsername, validPassword,invalidPassword);
        assertEquals("Passwords do not match",result);
    }
}