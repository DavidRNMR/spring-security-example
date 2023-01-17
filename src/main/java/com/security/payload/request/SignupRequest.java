package com.security.payload.request;

import com.security.entities.RoleEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

    @NotBlank
    @Size(min=3,max = 30)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<RoleEntity> role;

    @NotBlank
    @Size(min=6,max = 40)
    private String password;
}
