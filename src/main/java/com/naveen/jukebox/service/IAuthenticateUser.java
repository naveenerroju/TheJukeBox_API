package com.naveen.jukebox.service;

import com.naveen.jukebox.model.Credentials;

/**
 * Functional interface for authenticating users based on credentials.
 *
 * @deprecated Since version 0.0.2, scheduled for removal. Use alternative authentication methods.
 */
@FunctionalInterface
@Deprecated(since = "0.0.2", forRemoval = true)
public interface IAuthenticateUser {

    /**
     * Authenticates the user based on the provided credentials.
     *
     * @param credentials Credentials object containing username and password.
     * @return true if the user is authenticated successfully, false otherwise.
     */
    boolean authenticateUser(Credentials credentials);
}
