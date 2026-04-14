package br.com.medagenda.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterPatientDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "CPF is required") String cpf,
        @NotBlank(message = "Phone is required") String phone,
        @NotBlank(message = "Email is required") @Email String email,
        @NotBlank(message = "Password is required") @Size(min = 6) String password
) {}
