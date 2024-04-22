package com.naveen.jukebox.service;

import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.exceptions.BadCredentialsException;
import com.naveen.jukebox.exceptions.IncorrectInputsException;
import com.naveen.jukebox.model.Credentials;
import com.naveen.jukebox.model.UserRequest;
import com.naveen.jukebox.model.UserResponse;
import com.naveen.jukebox.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse createUser(UserRequest request) {

        checkIfUserExists(request);

        UserEntity entity = this.modelMapper.map(request, UserEntity.class);
        UserEntity responseEntity = userRepository.save(entity);

        return this.modelMapper.map(responseEntity, UserResponse.class);
    }

    private void checkIfUserExists(UserRequest request){
        Optional<UserEntity> entity = userRepository.findByUsername(request.getUsername());
        if(entity.isPresent()) {
            throw new IncorrectInputsException("This user already exists");
        }
    }

    @Override
    public boolean validateUser(Credentials credentials) {
        Optional<UserEntity> entity = userRepository.findByUsername(credentials.getUsername());
        if(entity.isPresent()){
            if(!entity.get().getPassword().equals(credentials.getPassword())){
                throw new BadCredentialsException("Invalid credentials");
            } else {
                return true;
            }
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
