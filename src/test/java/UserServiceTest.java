import blog.yrol.entity.User;
import blog.yrol.service.UserService;
import blog.yrol.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String reTypePassword;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl();
        firstName = "Yrol";
        lastName = "Fernando";
        email = "yrol@test.com";
        password = "abc1234";
        reTypePassword = "abc1234";
    }

    @DisplayName("User Object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {

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
