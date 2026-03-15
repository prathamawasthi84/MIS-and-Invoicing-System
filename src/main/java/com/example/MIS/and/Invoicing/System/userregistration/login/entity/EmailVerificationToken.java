package com.example.MIS.and.Invoicing.System.userregistration.login.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification_tokens")
public class EmailVerificationToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tokenId;
    String token;
    LocalDateTime expiresAt;
    @OneToOne
    @JoinColumn(name="user_id")
    public UserEntity userEntity;

    public EmailVerificationToken(){

    }
    //Getters
    public Integer getTokenId() {
        return tokenId;
    }
    public String getToken() {
        return token;
    }
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    //Setters
    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
