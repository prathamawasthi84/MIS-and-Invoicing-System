package com.example.MIS.and.Invoicing.System.userregistration.login.controller;

import com.example.MIS.and.Invoicing.System.userregistration.login.dto.UserDTO;
import com.example.MIS.and.Invoicing.System.userregistration.login.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    public AdminController (UserService userService){
        this.userService=userService;
    }
    @PostMapping("/add-user")
   public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){
        userService.saveUserByAdmin(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
   }
}
