package com.OficinaDeSoftware.Auth.config;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.OficinaDeSoftware.Auth.converter.UserConverter;
import com.OficinaDeSoftware.Auth.dto.CredentialsDto;
import com.OficinaDeSoftware.Auth.dto.UserDto;
import com.OficinaDeSoftware.Auth.model.RoleEnum;
import com.OficinaDeSoftware.Auth.service.UserService;
import com.OficinaDeSoftware.Auth.service.auth.AuthenticationService;

import jakarta.annotation.PostConstruct;

@Component
public class UserAuthenticationProvider {
    
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Autowired
    private AuthenticationService authenticationService; 

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    private String DS_TAG_ROLES = "roles";

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString( secretKey.getBytes() );
    }

    public String createToken( UserDto user ){

        Date now = new Date();
        Date validity = dtValidyToken( now );

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(user.getNrUuid())
                .withIssuedAt(now)
                .withClaim( DS_TAG_ROLES, RoleEnum.toString( user.getRoles() ) )
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    // TODO substituir pela mesma data do acess_token do provider 
    public Date dtValidyToken( Date now ){
        int oneHourInMilliseconds = 3600000;
        return new Date( now.getTime() + oneHourInMilliseconds );
    }

    public Authentication validateToken( String token ) {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        JWTVerifier verifier = JWT.require( algorithm ).build();

        final DecodedJWT decoded = verifier.verify( token );

        final UserDto user = userConverter.convertToDto( userService.findByNrUuid( decoded.getIssuer() ) );

        if( user == null ) {
            throw new RuntimeException("Invalid login");
        }

        final List<SimpleGrantedAuthority> authorityList = Arrays.stream( decoded.getClaim( DS_TAG_ROLES ).asArray( String.class ) ).map( SimpleGrantedAuthority::new ).collect( Collectors.toList() );

        return new UsernamePasswordAuthenticationToken( user, null, authorityList );
    }

    private List<SimpleGrantedAuthority> rolesEnumToSimpleGrantedAuthority( List<RoleEnum> roles ) {
        return roles.stream().map( current -> new SimpleGrantedAuthority( current.name() ) ).collect( Collectors.toList() );
    }

    public Authentication validateCredentials( CredentialsDto credentialsDto ) throws RuntimeException {
        final UserDto user = authenticationService.authenticate( credentialsDto );
        final List<SimpleGrantedAuthority> authorityList = rolesEnumToSimpleGrantedAuthority( user.getRoles() );
        return new UsernamePasswordAuthenticationToken( user, null, authorityList );
    }

}
