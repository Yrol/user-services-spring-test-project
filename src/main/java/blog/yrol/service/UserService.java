package blog.yrol.service;

import blog.yrol.entity.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String reTypePassword) throws IllegalArgumentException;
}
