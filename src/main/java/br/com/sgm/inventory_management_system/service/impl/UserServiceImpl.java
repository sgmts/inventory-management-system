package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.EnderecoUserDto;
import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.exceptions.EmailAlreadyRegisteredException;
import br.com.sgm.inventory_management_system.exceptions.ErrorDeletingUserException;
import br.com.sgm.inventory_management_system.exceptions.UserNotFoundException;
import br.com.sgm.inventory_management_system.mapper.EnderecoUserMapper;
import br.com.sgm.inventory_management_system.mapper.UserMapper;
import br.com.sgm.inventory_management_system.model.auth.EnderecoUser;
import br.com.sgm.inventory_management_system.model.auth.User;
import br.com.sgm.inventory_management_system.repository.UserRepository;
import br.com.sgm.inventory_management_system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.sgm.inventory_management_system.util.DateUtil.StringToLocalDate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final EnderecoUserMapper enderecoUserMapper;

    public UserDTO registerUser(UserDTO userDTO) {

        userDTO.CpfClean(userDTO.getCpf());

        User user = userMapper.toEntity(userDTO);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }

        // Criptografa a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Coloca a data do sistema no Cadastro do usuario
        user.setDataCadastro(LocalDateTime.now());

        // Salva o usuário no banco de dados
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserDTO> getAllUsers() {

        List<User> userList = userRepository.findAll();

        // Usando o UsuarioMapper para converter a lista
        return userList.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {

        Optional<User> requestedUser = userRepository.findById(id);

        return userMapper.toOptionalDto(requestedUser);
    }

    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (NoSuchElementException nsee) {
            throw new ErrorDeletingUserException();
        }
    }

    public void updateUser(Long id, UserDTO newUser) {

        // Busca o usuário existente no banco
        User userUpdated = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // Verifica se o e-mail já está cadastrado em outro registro
        userRepository.findByEmail(newUser.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new EmailAlreadyRegisteredException();
                    }
                });

        // Atualiza os campos do usuário
        userUpdated.setName(newUser.getName());
        userUpdated.setEmail(newUser.getEmail());
        userUpdated.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userUpdated.setCpf(newUser.getCpf());
        userUpdated.setTelefone(newUser.getTelefone());
        userUpdated.setDataNascimento(StringToLocalDate(newUser.getDataNascimento()));

        // Atualiza o endereço do usuário
        if (newUser.getEndereco() != null) {
            if (userUpdated.getEndereco() == null) {
                userUpdated.setEndereco(enderecoUserMapper.toEntity(newUser.getEndereco())); // Cria novo Endereço
            } else {
                EnderecoUser enderecoUpdated = userUpdated.getEndereco();
                EnderecoUserDto novoEndereco = newUser.getEndereco();

                enderecoUpdated.setCep(novoEndereco.getCep());
                enderecoUpdated.setLogradouro(novoEndereco.getLogradouro());
                enderecoUpdated.setNumero(novoEndereco.getNumero());
                enderecoUpdated.setComplemento(novoEndereco.getComplemento());
                enderecoUpdated.setBairro(novoEndereco.getBairro());
                enderecoUpdated.setCidade(novoEndereco.getCidade());
                enderecoUpdated.setUf(novoEndereco.getUf());
                enderecoUpdated.setRegiao(novoEndereco.getRegiao());
            }
        }
        userUpdated.setRole(newUser.getRole());

        // Salva o usuário atualizado
        userRepository.save(userUpdated);
    }
}