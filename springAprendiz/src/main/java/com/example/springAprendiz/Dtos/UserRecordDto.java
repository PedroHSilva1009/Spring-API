package com.example.springAprendiz.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UserRecordDto(@NotBlank String firstName, String middleName, @NotBlank String lastName,
                            @NotBlank @Email String email, @NotBlank String password, @NotBlank Integer userStatus,
                            @NotBlank Integer accessLevel, @NotBlank @CPF String CPF, @NotBlank String phone) {
}
