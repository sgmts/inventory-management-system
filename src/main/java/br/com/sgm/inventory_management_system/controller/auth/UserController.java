package br.com.sgm.inventory_management_system.controller.auth;

import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers(
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de Login");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<UserDTO>> getUserById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de buscar usuario com id {} no sistema.", id);

        var usuarioSolicitado = userService.getUserById(id);
        return new ResponseEntity<>(usuarioSolicitado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUserById(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de apagar usuario com id {} no sistema.", id);

        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO,
            @RequestHeader(name = "Authorization", required = true) String token) {
        log.info("Tentativa de atualizar usuario com id {} no sistema.", id);

        try {
            userService.updateUser(id, userDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}