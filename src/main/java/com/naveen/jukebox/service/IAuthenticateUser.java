package com.naveen.jukebox.service;

import com.naveen.jukebox.model.Credentials;

@FunctionalInterface
public interface IAuthenticateUser {
    public boolean authenticateUser(Credentials credentials);
}
