package com.ecommerce.project.securityjwt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3,max = 25)
    private String username;

    @NotBlank
    @Size(min = 6,max = 40)
    private String password;

    @NotBlank
    @Size(max = 25)
    @Email
    private String email;

    private Set<String> roles;

}
