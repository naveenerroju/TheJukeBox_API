package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.BadCredentialsException;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.Credentials;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;
import com.naveen.jukebox.model.UserRole;
import com.naveen.jukebox.repository.UserRepository;
import com.naveen.jukebox.validations.UserValidations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class for handling operations related to users.
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserValidations validations;
    private final ModelMapper modelMapper;

    /**
     * Constructs a UserService with necessary dependencies.
     *
     * @param userRepository Repository for UserEntity operations.
     * @param validations    Validations for UserEntity operations.
     * @param modelMapper    Mapper for mapping UserRequest and UserEntity.
     */
    @Autowired
    public UserService(UserRepository userRepository, UserValidations validations, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validations = validations;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new user based on the provided request.
     *
     * @param request UserRequest containing details of the user to be created.
     * @return UserResponse containing details of the created user.
     * @throws IncorrectInputsException if the user already exists.
     */
    @Override
    public UserResponse createUser(UserRequest request) {
        validations.checkIfUserExists(request);

        UserEntity entity = this.modelMapper.map(request, UserEntity.class);

        if (entity.getRole() == null) {
            entity.setRole(UserRole.USER);
        }

        UserEntity responseEntity = userRepository.save(entity);

        return this.modelMapper.map(responseEntity, UserResponse.class);
    }

    /**
     * Retrieves a user entity based on the username.
     *
     * @param userName Username of the user to retrieve.
     * @return UserEntity corresponding to the provided username.
     * @throws IncorrectInputsException if no user is found with the given username.
     */
    protected UserEntity getUserWithUsername(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        if (user.isEmpty()) {
            throw new IncorrectInputsException("User with username " + userName + " not found");
        } else {
            return user.get();
        }
    }

    /**
     * Validates user credentials.
     *
     * @param credentials Credentials (username and password) to validate.
     * @return true if the credentials are valid, false otherwise.
     * @throws BadCredentialsException if the provided credentials are invalid (username not found or password does not match).
     * @throws IllegalArgumentException if credentials are null.
     * @deprecated Use more specific methods for authentication, such as IAuthenticateUser.
     */
    @Override
    @Deprecated(since = "0.0.2", forRemoval = true)
    public boolean validateUser(Credentials credentials) {
        // Ensure credentials are not null
        Objects.requireNonNull(credentials, "Credentials must not be null.");

        // Extract username and password from credentials
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Retrieve user entity from repository based on username
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);

        // Check if user entity exists for the given username
        if (userEntityOptional.isPresent()) {
            // Get the user entity from Optional
            UserEntity userEntity = userEntityOptional.get();

            // Get the stored password from the user entity
            String storedPassword = userEntity.getPassword();

            // Compare stored password with provided password
            if (!storedPassword.equals(password)) {
                // Throw exception if passwords do not match
                throw new BadCredentialsException("Invalid credentials: incorrect password");
            }

            // Return true indicating valid credentials
            return true;
        } else {
            // Throw exception if no user entity found for the given username
            throw new BadCredentialsException("Invalid credentials: username not found");
        }
    }


}
