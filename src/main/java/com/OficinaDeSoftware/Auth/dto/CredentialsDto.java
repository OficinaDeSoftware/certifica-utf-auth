package com.OficinaDeSoftware.Auth.dto;

import com.OficinaDeSoftware.Auth.model.ProviderEnum;

// TODO take care of the exception IllegalArgumentException of ProviderEnum
public record CredentialsDto(
        ProviderEnum typeProvider,
        String idToken,
        String login,
        String password ) {
}
