import blog.yrol.entity.User;
import blog.yrol.service.UserService;
import blog.yrol.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    @Test
    void testCreateUser_whenUserDetailsProvided_returnsUserObject() {

        UserService userService = new UserServiceImpl();
        String firstName = "Yrol";
        String lastName = "Fernando";
        String email = "yrol@test.com";
        String password = "abc1234";
        String reTypePassword = "abc1234";

        User user = userService.createUser(firstName, lastName, email, password, reTypePassword);

        assertNotNull(user, "New user object should not be null");
    }
}
