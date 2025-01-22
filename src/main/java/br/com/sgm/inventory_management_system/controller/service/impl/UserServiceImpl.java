package br.com.sgm.inventory_management_system.controller.service.impl;

import br.com.sgm.inventory_management_system.controller.service.UserService;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.mapper.UserMapper;
import br.com.sgm.inventory_management_system.model.auth.User;
import br.com.sgm.inventory_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO registerUser(UserDTO userDTO) {

        User user = userMapper.mapDTOToModel(userDTO);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        // Criptografa a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Salva o usuário no banco de dados

        userRepository.save(user);
        return userMapper.mapModelToDTO(user);
    }

    public List<UserDTO> getAllUsers() {

        List<User> userList = userRepository.findAll();

        // Usando o UsuarioMapper para converter a lista
        return userList.stream()
                .map(userMapper::mapModelToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {

        Optional<User> requestedUser = userRepository.findById(id);

        return userMapper.mapModelToDTOOptional(requestedUser);
    }

    public void deteleUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, UserDTO newUser) {

        // Busca o usuário existente no banco
        User userUpdated = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        // Verifica se o e-mail já está cadastrado em outro registro
        userRepository.findByEmail(newUser.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new IllegalArgumentException("E-mail já cadastrado.");
                    }
                });

        // Atualiza os campos do usuário
        userUpdated.setName(newUser.getName());
        userUpdated.setEmail(newUser.getEmail());
        userUpdated.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userUpdated.setRole(newUser.getRole());

        // Salva o usuário atualizado
        userRepository.save(userUpdated);
    }
}