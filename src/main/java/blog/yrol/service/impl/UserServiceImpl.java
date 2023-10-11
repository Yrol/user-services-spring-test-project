package blog.yrol.service.impl;

import blog.yrol.entity.User;
import blog.yrol.repository.UserRepository;
import blog.yrol.service.EmailVerificationService;
import blog.yrol.service.UserService;
import blog.yrol.service.UserServiceException;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String reTypePassword) throws IllegalArgumentException {

        if(firstName.isBlank()) {
            throw new IllegalArgumentException("User first name is empty");
        }

        /*
         * Cannot be used this way for testing, therefore tt needs to be isolated as dependency injection as done above
         * **/
//        UserRepository userRepository = new UserRepositoryImpl();

        User user = new User(UUID.randomUUID().toString(), firstName, lastName, email, password, reTypePassword);
        boolean isUserCreated;

        try {
            isUserCreated = userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        if(!isUserCreated) throw new UserServiceException("Couldn't create user");

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }
        
        return user;
    }
}
