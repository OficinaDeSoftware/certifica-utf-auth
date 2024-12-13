package com.OficinaDeSoftware.Auth.dto;

import jakarta.validation.constraints.NotNull;

import com.OficinaDeSoftware.Auth.model.RoleEnum;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    @NotNull
    private String nrUuid;
    private String name;
    @NotNull
    private String email;
    private String accessToken;
    @NotNull
    private List<RoleEnum> roles;
}
