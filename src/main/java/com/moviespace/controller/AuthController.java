package com.moviespace.controller;

import com.moviespace.config.TokenService;
import com.moviespace.controller.request.LoginRequest;
import com.moviespace.controller.request.UserRequest;
import com.moviespace.controller.response.LoginResponse;
import com.moviespace.controller.response.UserResponse;
import com.moviespace.entity.User;
import com.moviespace.exceptions.UsernameOrPasswordInvalidException;
import com.moviespace.mapper.UserMapper;
import com.moviespace.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviespace/auth")
@RequiredArgsConstructor
@Tag(name = "User", description = "Rota resposável pelo gerenciamento de usuários")
public class AuthController {

    private final UserService userService;
    public final AuthenticationManager authenticationManager;
    public final TokenService tokenService;

    @Operation(summary = "Registrar usuário", description = "Método responsável por registrar um novo usuário",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        User savedUser = userService.save(UserMapper.toUser(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));

    }

    @Operation(summary = "Logar usuário", description = "Método responsável por efetuar o login de um usuário",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Usuário logado com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (BadCredentialsException e) {
            throw new UsernameOrPasswordInvalidException("Usuário ou senha inválidos!");
        }


    }
}
