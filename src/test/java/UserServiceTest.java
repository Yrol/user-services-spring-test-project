import blog.yrol.entity.User;
import blog.yrol.repository.UserRepository;
import blog.yrol.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

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
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        User user = userService.createUser(firstName, lastName, email, password, reTypePassword);

        assertNotNull(user, "New user object should not be null");
        assertNotNull(user.getId());
        assertEquals("Yrol", user.getFirstName(), "User first name does not match.");
        assertEquals("Fernando", user.getLastName(), "User last name does not match.");
        assertEquals("yrol@test.com", user.getEmail(), "User email does not match.");
    }


    @DisplayName("Empty first name causes expected exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwIllegalArgumentException() {

        firstName = "";

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, reTypePassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        assertEquals("User first name is empty", thrown.getMessage());
    }
}
