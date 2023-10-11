package blog.yrol.service.impl;

import blog.yrol.entity.User;
import blog.yrol.service.EmailVerificationService;

public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Override
    public void scheduleEmailConfirmation(User user) {
        System.out.println("scheduleEmailConfirmation invoked");
    }
}
