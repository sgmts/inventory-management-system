package br.com.sgm.inventory_management_system.controller.auth;

import br.com.sgm.inventory_management_system.controller.service.AuthService;
import br.com.sgm.inventory_management_system.controller.service.UserService;
import br.com.sgm.inventory_management_system.dto.JwtResponseDTO;
import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Inventory Management System", description = "Sistema de Gestão de Estoque Seguro")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AuthController {

    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(summary = "Registra Usuario", description = "Endpoint responsável por Registrar o Usuario no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuario registrado com sucesso."),
            @ApiResponse(responseCode = "403",description = "Campo invalido ou nulo."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login no sistema", description = "Endpoint responsável por realizar o login no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Login realizado com sucesso."),
            @ApiResponse(responseCode = "403",description = "Email e/ou senha invalido."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseDTO(token));
    }

}