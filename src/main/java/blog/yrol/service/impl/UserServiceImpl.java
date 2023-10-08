package blog.yrol.service.impl;

import blog.yrol.entity.User;
import blog.yrol.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String reTypePassword) {
        return new User(firstName, lastName, email, password, reTypePassword);
    }
}
