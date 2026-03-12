package com.example.MIS.and.Invoicing.System.userregistration.login.entity;

import com.example.MIS.and.Invoicing.System.userregistration.login.Status;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    @Column(name="password_hash",nullable = false)
    private String passwordHash;
    @Column(name="role",nullable = false)
    private String role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "created_at")
    private Timestamp createdAt;
    public UserEntity(){

    }
    public UserEntity(Integer userId,String fullName,String email,String passwordHash,String role,Timestamp createdAt){
        this.userId=userId;
        this.fullName=fullName;
        this.email=email;
        this.passwordHash=passwordHash;
        this.role=role;
        this.createdAt=createdAt;
    }
    //Getters
    public Integer getUserId() {
        return userId;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public String getRole() {
        return role;
    }
    public Status getStatus() {
        return status;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    //Setters
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
