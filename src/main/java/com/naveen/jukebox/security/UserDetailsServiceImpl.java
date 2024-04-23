package com.naveen.jukebox.security;

import com.naveen.jukebox.entity.UserEntity;
import com.naveen.jukebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = repository.findByUsername(username).get();
        CustomUserDetails userDetails = new CustomUserDetails(entity);
        return userDetails;
    }
}
