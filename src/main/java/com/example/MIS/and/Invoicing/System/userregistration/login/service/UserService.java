package com.example.MIS.and.Invoicing.System.userregistration.login.service;

import com.example.MIS.and.Invoicing.System.userregistration.login.Status;
import com.example.MIS.and.Invoicing.System.userregistration.login.config.SecurityConfig;
import com.example.MIS.and.Invoicing.System.userregistration.login.dto.UserDTO;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.UserEntity;
import com.example.MIS.and.Invoicing.System.userregistration.login.mapper.UserMapper;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,UserMapper userMapper){
        this.userMapper=userMapper;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public UserEntity saveUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity userEntity  = userMapper.toEntity(userDTO,encodedPassword);
        userEntity.setRole("USER");
        userEntity.setStatus(Status.ACTIVE);
        return userRepository.save(userEntity);
    }
}
