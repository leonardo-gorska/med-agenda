package br.com.medagenda.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDoctorDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "CRM is required") String crm,
        @NotBlank(message = "Specialty is required") String specialty,
        @NotBlank(message = "Email is required") @Email String email,
        @NotBlank(message = "Password is required") @Size(min = 6) String password
) {}
