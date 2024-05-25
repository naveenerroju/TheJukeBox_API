package com.naveen.jukebox.controller;

import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;
import com.naveen.jukebox.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Controller for user registration in the Jukebox application.
 * <p>
 * This controller provides an endpoint for registering a new user.
 * It interacts with the UserService to perform the registration operation and returns the appropriate HTTP response.
 * </p>
 */
@RestController
@RequestMapping("/register")
public class RegisterUserController {

    private final UserService userService;

    /**
     * Constructs a new RegisterUserController with the specified userService.
     *
     * @param userService the UserService used to perform user registration operation
     */
    public RegisterUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     * <p>
     * This endpoint accepts a UserRequest object containing user details and returns a UserResponse object
     * with the registration details if successful.
     * </p>
     *
     * @param request the UserRequest object containing the user's registration details
     * @return a ResponseEntity containing the UserResponse and HTTP status 201 (Created) if the operation is successful
     */
    @Operation(summary = "Registers a new user.", description = "Registers a new user with the given details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @PostMapping(path = "/user")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }
}
