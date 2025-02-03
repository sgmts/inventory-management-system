package br.com.sgm.inventory_management_system.controller.auth;

import br.com.sgm.inventory_management_system.dto.JwtResponseDtO;
import br.com.sgm.inventory_management_system.dto.LoginRequestDto;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.service.AuthService;
import br.com.sgm.inventory_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Tentativa de registro para o email: {}", userDTO.getEmail());
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDtO> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        log.info("Tentativa de login para o email: {}", loginRequest.getEmail());
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseDtO(token));
    }

}