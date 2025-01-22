package br.com.sgm.inventory_management_system.controller.service;


import br.com.sgm.inventory_management_system.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO registerUser(UserDTO user);

    void deleteUserById(Long id);

    void updateUser(Long id, UserDTO userDTO);
}