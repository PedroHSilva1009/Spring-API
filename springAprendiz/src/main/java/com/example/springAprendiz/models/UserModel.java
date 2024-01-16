package com.example.springAprendiz.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "user")
public class UserModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID UserId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Integer userStatus;
    private Integer acessLevel;

    public UUID getUserId() {
        return UserId;
    }

    public void setUserId(UUID userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getAcessLevel() {
        return acessLevel;
    }

    public void setAcessLevel(Integer acessLevel) {
        this.acessLevel = acessLevel;
    }
}
