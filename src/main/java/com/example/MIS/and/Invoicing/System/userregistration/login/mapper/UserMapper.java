package com.example.MIS.and.Invoicing.System.userregistration.login.mapper;

import com.example.MIS.and.Invoicing.System.userregistration.login.dto.UserDTO;
import com.example.MIS.and.Invoicing.System.userregistration.login.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserDTO userDTO,String encodedPassword){
        UserEntity userEntity= new UserEntity();
        userEntity.setFullName(userDTO.getFullName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPasswordHash(encodedPassword);
        return userEntity;
    }
    public UserDTO toResponse(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setEmail(userEntity.getEmail());
        return userDTO;
    }

}
