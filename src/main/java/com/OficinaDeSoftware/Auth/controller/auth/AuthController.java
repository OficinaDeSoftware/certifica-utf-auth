package com.OficinaDeSoftware.Auth.controller.auth;

import com.OficinaDeSoftware.Auth.dto.response.MessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import com.OficinaDeSoftware.Auth.config.UserAuthenticationProvider;
import com.OficinaDeSoftware.Auth.dto.CredentialsDto;
import com.OficinaDeSoftware.Auth.dto.ErrorDto;
import com.OficinaDeSoftware.Auth.dto.UserDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Tag( name = "Auth")
@RequestMapping( value = "/api/auth", produces = {"application/json"} )
public class AuthController {

    private final UserAuthenticationProvider userAuthenticationProvider;

    public AuthController( UserAuthenticationProvider userAuthenticationProvider ) {
        this.userAuthenticationProvider = userAuthenticationProvider;
    }
    
    @Operation( summary = "Realiza a autenticação do usuario", method = "POST")
    
    @ApiResponses( value = {
        @ApiResponse( responseCode = "200", description = "Usuario autenticado com sucesso!", content = { @Content( schema = @Schema( implementation = UserDto.class ) ) }),
        @ApiResponse( responseCode = "401", description = "Acesso não autorizado!", content = { @Content( schema = @Schema( implementation = ErrorDto.class ) )})
    } )

    @RequestBody(description = "Dados necessários para autenticação do usuario", required = true,
    content = @Content( schema=@Schema(implementation = CredentialsDto.class ) ) )

   @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn( @AuthenticationPrincipal UserDto user ){
        user.setAccessToken(userAuthenticationProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/validate/token")
    public ResponseEntity<MessageResponseDto> validateToken() {
        return ResponseEntity.ok( new MessageResponseDto("This token is valid") );
    }
}
