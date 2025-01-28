package br.com.sgm.inventory_management_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Configuração principal da documentação da API
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão de Usuários")
                        .version("1.0")
                        .description("Documentação da API com autenticação JWT"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Define o esquema de segurança global
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", // Nome do esquema de segurança
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // Tipo HTTP
                                        .scheme("bearer") // Bearer Token
                                        .bearerFormat("JWT") // Formato JWT
                        ));
    }
}
