package com.naveen.jukebox.validations;

import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidations {

    @Autowired
    private UserRepository userRepository;

    public void checkIfUserExists(UserRequest request){
        Optional<UserEntity> entity = userRepository.findByUsername(request.getUsername());
        if(entity.isPresent()) {
            throw new IncorrectInputsException("This user already exists");
        }
    }
}
