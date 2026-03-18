package com.example.MIS.and.Invoicing.System.userregistration.login.service;

import com.example.MIS.and.Invoicing.System.userregistration.login.entity.UserEntity;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        System.out.println("Loading user: " + email);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        System.out.println("Role: " + user.getRole());
        System.out.println("Status: " + user.getStatus());

        return User.builder()
            .username(user.getEmail())
            .password(user.getPasswordHash())
            .authorities(user.getRole())
            .build();
    }
}
