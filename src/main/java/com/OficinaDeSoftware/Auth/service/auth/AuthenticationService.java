package com.OficinaDeSoftware.Auth.service.auth;

import java.util.List;

import com.OficinaDeSoftware.Auth.service.auth.Provider.ProviderTokenServiceFactory;
import com.OficinaDeSoftware.Auth.service.exception.UnknowProviderTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OficinaDeSoftware.Auth.converter.UserConverter;
import com.OficinaDeSoftware.Auth.domain.User;
import com.OficinaDeSoftware.Auth.dto.CredentialsDto;
import com.OficinaDeSoftware.Auth.dto.UserDto;
import com.OficinaDeSoftware.Auth.model.ProviderModel;
import com.OficinaDeSoftware.Auth.model.RoleEnum;
import com.OficinaDeSoftware.Auth.service.UserService;
import com.OficinaDeSoftware.Auth.service.auth.Provider.ProviderTokenService;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    private final ProviderTokenServiceFactory providerTokenServiceFactory;

    public AuthenticationService( ProviderTokenServiceFactory providerTokenServiceFactory ){
        this.providerTokenServiceFactory = providerTokenServiceFactory;
    }
    
    public UserDto authenticate( CredentialsDto credentialsDto ) throws RuntimeException {

        ProviderTokenService providerTokenService = providerTokenServiceFactory.getService( credentialsDto.typeProvider() );

        if( providerTokenService == null ) {
            throw new UnknowProviderTokenException();
        }

        final ProviderModel provider = providerTokenService.process( credentialsDto );

        final User registeredUser = userService.findByNrUuid( provider.getNrUuid() );

        if( registeredUser != null ){
            return userConverter.convertToDto( registeredUser );
        }

        return registerUser( provider );
    }

    public UserDto registerUser( final ProviderModel provider ) {

        UserDto userDto = userConverter.convertToDto( provider );
        userDto.setRoles( List.of( RoleEnum.ROLE_USER ) );
        userService.save( userDto );

        return userDto;
    }
}
