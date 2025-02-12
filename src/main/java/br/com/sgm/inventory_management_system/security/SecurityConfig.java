package br.com.sgm.inventory_management_system.security;

import br.com.sgm.inventory_management_system.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.sgm.inventory_management_system.model.auth.RoleEnum.ADMIN;
import static br.com.sgm.inventory_management_system.model.auth.RoleEnum.OPERATOR;

@Configuration
@EnableMethodSecurity // Habilita o uso de @PreAuthorize e @PostAuthorize
@SecurityScheme(name = SecurityConfig.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfig {

    public static final String SECURITY = "bearerAuth";
    public static final String URI_REGISTER = "api/auth/register";
    public static final String URI_LOGIN = "api/auth/login";
    public static final String URI_USER_ROLE = "/api/users/**";
    public static final String URI_PRODUCT_ROLE = "/api/product";
    public static final String URI_FIND_ZIPCODE = "/api/buscar-cep/**";
    public static final String URI_CATEGORY_ROLE = "/api/category/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, URI_REGISTER).permitAll()
                        .requestMatchers(HttpMethod.POST, URI_LOGIN).permitAll()
                        .requestMatchers(HttpMethod.GET, URI_FIND_ZIPCODE).permitAll()
                        .requestMatchers(URI_USER_ROLE).hasRole(ADMIN.name()) // Apenas ADMIN pode acessar
                        .requestMatchers(URI_PRODUCT_ROLE).hasAnyRole(OPERATOR.name(), ADMIN.name())
                        .requestMatchers(URI_CATEGORY_ROLE).hasAnyRole(OPERATOR.name(), ADMIN.name())
                        .anyRequest().authenticated() // Qualquer outro endpoint requer autenticação
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean para codificar senhas com BCrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}