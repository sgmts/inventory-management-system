package br.com.sgm.inventory_management_system.controller.service.impl;

import br.com.sgm.inventory_management_system.controller.service.AuthService;
import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;
import br.com.sgm.inventory_management_system.model.auth.User;
import br.com.sgm.inventory_management_system.repository.UserRepository;
import br.com.sgm.inventory_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(LoginRequestDTO loginRequest) {
        // Verifica se o usuário existe
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        // Verifica a senha
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        // Gera o token JWT incluindo o papel do usuário
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}