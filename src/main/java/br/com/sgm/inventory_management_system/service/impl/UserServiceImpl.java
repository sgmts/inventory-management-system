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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final EnderecoUserMapper enderecoUserMapper;

    public void registerUser(UserDTO userDTO) {
        log.info("Iniciando o registro do usuário com e-mail: {}", userDTO.getEmail());

        userDTO.removeCpfFormatting(userDTO.getCpf());
        userDTO.getEndereco().removeCepFormatting(userDTO.getEndereco().getCep());

        User user = userMapper.toEntity(userDTO);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Tentativa de registro com e-mail já cadastrado: {}", user.getEmail());
            throw new EmailAlreadyRegisteredException();
        }

        // Criptografa a senha
        log.info("Criptografando a senha para o e-mail: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Coloca a data do sistema no Cadastro do usuario
        user.setDataCadastro(LocalDateTime.now());
        log.info("Data de cadastro definida para o e-mail: {}", user.getEmail());

        // Salva o usuário no banco de dados
        userRepository.save(user);
        log.info("Usuário com e-mail {} registrado com sucesso!", user.getEmail());
    }

    public List<UserDTO> getAllUsers() {
        log.info("Iniciando a busca de usuarios no sistema.");

        List<User> userList = userRepository.findAll();
        log.info("Número total de usuários encontrados: {}", userList.size());

        // Usando o UsuarioMapper para converter a lista
        List<UserDTO> userDTOList = userList.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        log.info("Conversão para DTOs concluída. Total de usuários processados: {}", userDTOList.size());

        return userDTOList;
    }

    public Optional<UserDTO> getUserById(Long id) {
        log.info("Iniciando a busca do usuario {} no sistema.", id);

        Optional<User> requestedUser = userRepository.findById(id);

        // Verifica se o usuário foi encontrado
        if (requestedUser.isEmpty()) {
            log.warn("Usuário com ID {} não encontrado no sistema.", id);
            throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");
        }

        log.info("Usuário com ID {} encontrado no sistema.", id);
        return userMapper.toOptionalDto(requestedUser);
    }

    public void deleteUserById(Long id) {
        log.info("Iniciando a exclusão do usuário com ID {} no sistema.", id);

        // Verifica se o usuário existe
        if (!userRepository.existsById(id)) {
            log.warn("Tentativa de excluir usuário com ID {} que não existe.", id);
            throw new UserNotFoundException("Usuário com ID " + id + " não encontrado no sistema.");
        }

        // Realiza a exclusão do usuário
        try {
            userRepository.deleteById(id);
            log.info("Usuário com ID {} excluído com sucesso.", id);
        } catch (Exception ex) {
            log.error("Erro ao excluir o usuário com ID {}. Detalhes: {}", id, ex.getMessage());
            throw new ErrorDeletingUserException("Erro ao excluir o usuário com ID " + id + ".");
        }
    }

    public void updateUser(Long id, UserDTO newUser) {
        log.info("Iniciando a atualização do usuário com id {} no sistema.", id);

        // Busca o usuário existente no banco
        User userUpdated = userRepository.findById(id)
                .orElseThrow( () -> {
                  log.warn("Usuário com ID {} nao encontrado no sistema", id);
                    return new UserNotFoundException();
                });

        // Verifica se o e-mail já está cadastrado em outro registro
        userRepository.findByEmail(newUser.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        log.warn("Tentativa de atualizar usuário com e-mail já cadastrado: {}", newUser.getEmail());
                        throw new EmailAlreadyRegisteredException();
                    }
                });

        // Atualiza os campos do usuário
        log.info("Atualizando os dados do usuário com ID {}.", id);
        userUpdated.setName(newUser.getName());
        userUpdated.setEmail(newUser.getEmail());
        userUpdated.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userUpdated.setCpf(newUser.getCpf());
        userUpdated.setTelefone(newUser.getTelefone());
        userUpdated.setDataNascimento(newUser.getDataNascimento());

        // Atualiza o endereço do usuário
        if (newUser.getEndereco() != null) {
            if (userUpdated.getEndereco() == null) {
                log.info("Criando um novo endereço para o usuário com ID {}.", id);
                userUpdated.setEndereco(enderecoUserMapper.toEntity(newUser.getEndereco())); // Cria novo Endereço
            } else {
                log.info("Atualizando o endereço existente do usuário com ID {}.", id);
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
        log.info("Usuário com ID {} atualizado com sucesso.", id);
    }
}