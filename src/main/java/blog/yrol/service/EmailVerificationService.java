package blog.yrol.service;

import blog.yrol.entity.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
