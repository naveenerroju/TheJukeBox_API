package com.naveen.jukebox.service;

import com.naveen.jukebox.model.Credentials;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;

public interface IUserService {

    UserResponse createUser(UserRequest request);
    boolean validateUser(Credentials credentials);
}
