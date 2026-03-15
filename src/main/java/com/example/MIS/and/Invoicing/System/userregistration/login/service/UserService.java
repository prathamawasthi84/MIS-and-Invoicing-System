package com.example.MIS.and.Invoicing.System.userregistration.login.service;

import com.example.MIS.and.Invoicing.System.userregistration.login.Status;
import com.example.MIS.and.Invoicing.System.userregistration.login.config.SecurityConfig;
import com.example.MIS.and.Invoicing.System.userregistration.login.dto.UserDTO;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.EmailVerificationToken;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.UserEntity;
import com.example.MIS.and.Invoicing.System.userregistration.login.mapper.UserMapper;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.EmailVerifiactionTokenRespository;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailVerifiactionTokenRespository emailVerifiactionTokenRespository;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,UserMapper userMapper,EmailVerifiactionTokenRespository emailVerifiactionTokenRespository){
        this.userMapper=userMapper;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.emailVerifiactionTokenRespository = emailVerifiactionTokenRespository;
    }
    public UserEntity saveUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity userEntity  = userMapper.toEntity(userDTO,encodedPassword);
        userEntity.setRole("USER");
        userEntity.setStatus(Status.PENDING);
        String token = UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUserEntity(userEntity);
        emailVerificationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        emailVerifiactionTokenRespository.save(emailVerificationToken);
        return userRepository.save(userEntity);
    }
}
