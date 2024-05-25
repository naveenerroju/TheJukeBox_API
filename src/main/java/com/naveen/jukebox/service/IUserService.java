package com.naveen.jukebox.service;

import com.naveen.jukebox.model.Credentials;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;

/**
 * Interface for managing user-related operations.
 */
public interface IUserService {

    /**
     * Creates a new user based on the provided request.
     *
     * @param request UserRequest object containing user details.
     * @return UserResponse object containing the created user details.
     */
    UserResponse createUser(UserRequest request);

    /**
     * Validates the user based on the provided credentials.
     *
     * @param credentials Credentials object containing username and password.
     * @return true if the credentials are valid, false otherwise.
     * @since 2024-05-27
     * @deprecated Use more specific methods for authentication, such as IAuthenticateUser.
     */
    @Deprecated(since = "0.0.2", forRemoval = true)
    boolean validateUser(Credentials credentials);
}
