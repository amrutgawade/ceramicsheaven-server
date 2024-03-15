package com.ceramicsheaven.services;

import com.ceramicsheaven.model.User;
import com.ceramicsheaven.exceptions.UserException;

public interface UserService {

    public User findById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User updateUser(String jwt,User user) throws UserException;
}
