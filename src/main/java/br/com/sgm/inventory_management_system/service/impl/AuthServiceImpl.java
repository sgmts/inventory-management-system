package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;
import br.com.sgm.inventory_management_system.exceptions.InvalidCredentialsException;
import br.com.sgm.inventory_management_system.model.auth.User;
import br.com.sgm.inventory_management_system.repository.UserRepository;
import br.com.sgm.inventory_management_system.service.AuthService;
import br.com.sgm.inventory_management_system.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String authenticate(LoginRequestDTO loginRequest) {
        // Verifica se o usuário existe
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // Verifica a senha
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Gera o token JWT incluindo o papel do usuário
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}