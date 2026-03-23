package com.cognizant.safeschool.service;

import com.cognizant.safeschool.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        UserDetails userDetails=userRepository.findUserByEmail(email);

        if(userDetails==null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return userDetails;
    }
}
