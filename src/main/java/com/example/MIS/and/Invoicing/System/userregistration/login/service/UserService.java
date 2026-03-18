package com.example.MIS.and.Invoicing.System.userregistration.login.service;

import com.example.MIS.and.Invoicing.System.userregistration.login.Status;
import com.example.MIS.and.Invoicing.System.userregistration.login.config.SecurityConfig;
import com.example.MIS.and.Invoicing.System.userregistration.login.dto.UserDTO;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.EmailVerificationToken;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.UserEntity;
import com.example.MIS.and.Invoicing.System.userregistration.login.mapper.UserMapper;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.EmailVerifiactionTokenRespository;
import com.example.MIS.and.Invoicing.System.userregistration.login.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailVerifiactionTokenRespository emailVerifiactionTokenRespository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,EmailService emailService,PasswordEncoder passwordEncoder,UserMapper userMapper,EmailVerifiactionTokenRespository emailVerifiactionTokenRespository){
        this.userMapper=userMapper;
        this.emailService=emailService;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.emailVerifiactionTokenRespository = emailVerifiactionTokenRespository;
    }
    public UserEntity saveUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity userEntity = userMapper.toEntity(userDTO, encodedPassword);
        userEntity.setRole("USER");
        userEntity.setStatus(Status.PENDING);

        UserEntity savedUser = userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setUserEntity(savedUser);
        emailVerificationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        emailVerifiactionTokenRespository.save(emailVerificationToken);

        emailService.sendVerificationMail(savedUser.getEmail(), token);

        return savedUser;
    }
    public UserEntity saveUserByAdmin(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        UserEntity userEntity = userMapper.toEntity(userDTO,encodedPassword);
        userEntity.setRole(userDTO.getRole());
        userEntity.setStatus(Status.ACTIVE);
        return userRepository.save(userEntity);
    }
    public String verifyEmail(String token) {
        Optional<EmailVerificationToken> output = emailVerifiactionTokenRespository.findByToken(token);
        if (output.isEmpty()) {
            throw new RuntimeException("Invalid Token");
        }
        EmailVerificationToken emailVerificationToken = output.get();
        if (emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token Expired");
        }
        UserEntity userEntity = emailVerificationToken.getUserEntity();
        userEntity.setStatus(Status.ACTIVE);
        userRepository.save(userEntity);
        return "Email Verified Successfully";
    }
}
