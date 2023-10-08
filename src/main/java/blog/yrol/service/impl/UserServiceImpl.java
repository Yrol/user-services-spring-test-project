package blog.yrol.service.impl;

import blog.yrol.entity.User;
import blog.yrol.service.UserService;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String reTypePassword) throws IllegalArgumentException {

        if(firstName.isBlank()) {
            throw new IllegalArgumentException("User first name is empty");
        }

        return new User(UUID.randomUUID().toString(), firstName, lastName, email, password, reTypePassword);
    }
}
