import blog.yrol.entity.User;
import blog.yrol.repository.UserRepository;
import blog.yrol.service.UserService;
import blog.yrol.service.UserServiceException;
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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     * Adding UserServiceImpl and allowing mock object to be added to it.
     * **/
    @InjectMocks
    UserServiceImpl userService;

    /**
     * Adding UserRepository mock object
     * **/
    @Mock
    UserRepository userRepository;
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
         * Making the sure the userRepository.save() method was called exactly one time (although we don't test this code as mentioned above)
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
     * Exception stubbing
     * **/
    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {

        /**
         * Mocking the save method to throw a RuntimeException
         * **/
        when(userRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, reTypePassword);
        }, "Should have thrown UserServiceException instead");
    }
}
