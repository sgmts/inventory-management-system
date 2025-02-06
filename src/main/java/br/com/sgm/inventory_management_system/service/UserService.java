package br.com.sgm.inventory_management_system.service;


import br.com.sgm.inventory_management_system.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    void registerUser(UserDTO user);

    void deleteUserById(Long id);

    void updateUser(Long id, UserDTO userDTO);
}