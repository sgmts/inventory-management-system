package br.com.sgm.inventory_management_system.controller.auth;

import br.com.sgm.inventory_management_system.controller.service.UserService;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Controle de Usuarios", description = "Controle de Usuarios do sistema")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca todos os Usuarios", description = "Endpoint responsável por trazer todos os Usuario no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista entregue"),
            @ApiResponse(responseCode = "403",description = "Token Invalido."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public List<UserDTO> getAllUsers(
            @RequestHeader(name = "Authorization", required = true) String token) {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Busca Usuario por ID", description = "Endpoint responsável por trazer um usuário específico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuario encontrado com sucesso."),
            @ApiResponse(responseCode = "403",description = "Token Invalido."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public ResponseEntity<Optional<UserDTO>> getUserById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {

        var usuarioSolicitado = userService.getUserById(id);
        return new ResponseEntity<>(usuarioSolicitado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deleta Usuario", description = "Endpoint responsável por apagar um usuário específico por ID do Sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuario deletado com sucesso."),
            @ApiResponse(responseCode = "403",description = "Token Invalido."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public ResponseEntity<HttpStatus> deleteUserById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {

        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualiza Usuario no Sistema", description = "Endpoint responsável por atualizar um usuário específico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuario atualizado com sucesso."),
            @ApiResponse(responseCode = "403",description = "Token Invalido."),
            @ApiResponse(responseCode = "500",description = "Erro Interno do servidor."),
    })
    public ResponseEntity<UserDTO> updateUserById(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO,
            @RequestHeader(name = "Authorization", required = true) String token) {

        try {
            userService.updateUser(id, userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}