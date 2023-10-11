import blog.yrol.entity.User;
import blog.yrol.repository.UserRepository;
import blog.yrol.service.EmailNotificationException;
import blog.yrol.service.EmailVerificationService;
import blog.yrol.service.UserService;
import blog.yrol.service.UserServiceException;
import blog.yrol.service.impl.EmailVerificationServiceImpl;
import blog.yrol.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     * Adding UserServiceImpl and allowing mock object to be added to it.
     **/
    @InjectMocks
    UserServiceImpl userService;

    /**
     * Adding UserRepository mock object
     **/
    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String reTypePassword;

    @BeforeEach
    void init() {
//        userService = new UserServiceImpl();
        firstName = "Yrol";
        lastName = "Fernando";
        email = "yrol@test.com";
        password = "abc1234";
        reTypePassword = "abc1234";
    }

    @DisplayName("User Object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {

        /**
         * Mocking the userRepository and set its return value to true (mocking as user created successfully, and if set to false, the test will fail).
         * The real userRepository.save() will not be executed since this is a mock (try with setting a debug point inside save())
         * Using "Mockito.any" to create a User object on the fly instead of a pre-defined one.
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        User user = userService.createUser(firstName, lastName, email, password, reTypePassword);

        assertNotNull(user, "New user object should not be null");
        assertNotNull(user.getId());
        assertEquals("Yrol", user.getFirstName(), "User first name does not match.");
        assertEquals("Fernando", user.getLastName(), "User last name does not match.");
        assertEquals("yrol@test.com", user.getEmail(), "User email does not match.");

        /**
         * Making the sure the userRepository.save() method will be called in a real scenario, although it won't be executed / run during test run (since only mocking)
         * **/
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }


    @DisplayName("Empty first name causes expected exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwIllegalArgumentException() {

        firstName = "";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, reTypePassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        assertEquals("User first name is empty", thrown.getMessage());
    }

    /**
     * Exception stubbing for UerService createUser()
     **/
    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {

        /**
         * Mocking the save method to throw a RuntimeException
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, reTypePassword);
        }, "Should have thrown UserServiceException instead");
    }

    /**
     * Testing void methods using Mockito -  emailVerificationService.scheduleEmailConfirmation(user);
     **/
    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_thenThrowsUserServiceException() {

        /**
         * Non-void userRepository.save
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        /**
         * Handling the void method
         * **/
        //when(emailVerificationService.scheduleEmailConfirmation(Mockito.any(User.class))).thenThrow(EmailNotificationException.class); // Won't work as in void method

        // Mocking the emailVerificationService to throw EmailNotificationException
        doThrow(EmailNotificationException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(Mockito.any(User.class));

        assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, reTypePassword);
        });
    }

    /**
     * Avoiding the void methods after invoke -  emailVerificationService.scheduleEmailConfirmation(user);
     **/
    @DisplayName("Avoiding EmailNotification when invoked")
    @Test
    void testCreateUser_whenEmailNotification_thenReturnsUserObject() {

        /**
         * Mocking Non-void userRepository.save
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        /**
         * Handling the void method
         * **/
        //when(emailVerificationService.scheduleEmailConfirmation(Mockito.any(User.class))).thenThrow(EmailNotificationException.class); // Won't work as in void method

        // Mocking the emailVerificationService and do nothing
        doNothing().when(emailVerificationService).scheduleEmailConfirmation(Mockito.any(User.class));

        /**
         * Calling the userService.createUser
         * **/
        userService.createUser(firstName, lastName, email, password, reTypePassword);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    /**
     * Executing dependency methods EmailVerificationService
     * **/
    @DisplayName("EmailVerificationService is executed")
    @Test
    void testCreateUser_whenUserCreated_scheduleEmailConfirmation() {
        /**
         * Mocking Non-void userRepository.save
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        /**
         * Invoking /  calling the method without mocking
         * **/
        Mockito.doCallRealMethod().when(emailVerificationService).
                scheduleEmailConfirmation(Mockito.any(User.class));

        userService.createUser(firstName, lastName, email, password, reTypePassword);

        /**
         * Making sure scheduleEmailConfirmation() is called once
         * **/
        verify(emailVerificationService, atLeast(1)).scheduleEmailConfirmation(any(User.class));
    }
}