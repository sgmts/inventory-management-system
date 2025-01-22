package br.com.sgm.inventory_management_system.controller.auth;

import br.com.sgm.inventory_management_system.controller.service.AuthService;
import br.com.sgm.inventory_management_system.controller.service.UserService;
import br.com.sgm.inventory_management_system.dto.JwtResponseDTO;
import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok("Usuário registrado com sucesso: " + registeredUser.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String token = authService.authenticate(loginRequest);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

}