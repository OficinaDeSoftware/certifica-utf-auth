package com.OficinaDeSoftware.Auth.service.auth.Provider;

import com.OficinaDeSoftware.Auth.dto.CredentialsDto;

import com.OficinaDeSoftware.Auth.model.ProviderModel;

public interface ProviderTokenService {
    public ProviderModel process( final CredentialsDto credentialsDto ) throws RuntimeException;
}
