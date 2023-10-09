package blog.yrol.repository.impl;

import blog.yrol.entity.User;
import blog.yrol.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple repository class that will mimic the DB insertions using a hashMap
 * **/

public class UserRepositoryImpl implements UserRepository {

    Map<String, User> users = new HashMap<>();

    @Override
    public boolean save(User user) {
        if(!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        }
        return false;
    }
}
