package com.naveen.jukebox.validations;

import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class provides validation methods for user-related operations.
 */
@Component
public class UserValidations {

    private final UserRepository userRepository;

    /**
     * Constructs a UserValidations object with the specified UserRepository.
     *
     * @param userRepository The repository to retrieve user information.
     */
    @Autowired
    public UserValidations(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if a user already exists based on the username in the UserRequest.
     *
     * @param request The UserRequest object containing the username to be checked.
     * @throws IncorrectInputsException if the username already exists in the database.
     */
    public void checkIfUserExists(UserRequest request) {
        Optional<UserEntity> entity = userRepository.findByUsername(request.getUsername());
        if (entity.isPresent()) {
            throw new IncorrectInputsException("This user already exists");
        }
    }
}
