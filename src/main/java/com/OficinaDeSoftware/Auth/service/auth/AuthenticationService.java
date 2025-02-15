package com.OficinaDeSoftware.Auth.service.auth;

import java.util.List;

import com.OficinaDeSoftware.Auth.producer.EmailProducer;
import com.OficinaDeSoftware.Auth.service.auth.Provider.ProviderTokenServiceFactory;
import com.OficinaDeSoftware.Auth.service.exception.UnknowProviderTokenException;
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

    private final UserService userService;
    private final UserConverter userConverter;
    private final EmailProducer emailProducer;
    private final ProviderTokenServiceFactory providerTokenServiceFactory;

    public AuthenticationService(
            ProviderTokenServiceFactory providerTokenServiceFactory,
            UserConverter userConverter,
            UserService userService,
            EmailProducer emailProducer ){
        this.providerTokenServiceFactory = providerTokenServiceFactory;
        this.userConverter = userConverter;
        this.userService = userService;
        this.emailProducer = emailProducer;
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

        emailProducer.register( userDto );

        return userDto;
    }
}
