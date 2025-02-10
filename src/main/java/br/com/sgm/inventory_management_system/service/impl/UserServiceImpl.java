package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.user.UserAddressDto;
import br.com.sgm.inventory_management_system.dto.user.UserDTO;
import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;
import br.com.sgm.inventory_management_system.exceptions.EmailAlreadyRegisteredException;
import br.com.sgm.inventory_management_system.exceptions.ErrorDeletingUserException;
import br.com.sgm.inventory_management_system.exceptions.UserNotFoundException;
import br.com.sgm.inventory_management_system.mapper.UserAddressMapper;
import br.com.sgm.inventory_management_system.mapper.UserMapper;
import br.com.sgm.inventory_management_system.model.auth.User;
import br.com.sgm.inventory_management_system.model.auth.UserAddress;
import br.com.sgm.inventory_management_system.repository.UserRepository;
import br.com.sgm.inventory_management_system.service.ZipCodeService;
import br.com.sgm.inventory_management_system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final UserAddressMapper enderecoUserMapper;

    private final ZipCodeService zipCodeService;


    public void registerUser(UserDTO userDTO) {
        log.info("Iniciando o registro do usuário com e-mail: {}", userDTO.getEmail());

        // Obter resposta da API ViaCEP
        ViaCepResponseDto cepResponse = zipCodeService.findAddress(userDTO.getAddress().getCep());

        enderecoUserMapper.updateUserAddressFromViaCep(cepResponse, userDTO.getAddress());

        userDTO.removeCpfFormatting(userDTO.getCpf());

        userDTO.getAddress().removeCepFormatting(userDTO.getAddress().getCep());

        User user = userMapper.toEntity(userDTO);

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Tentativa de registro com e-mail já cadastrado: {}", user.getEmail());
            throw new EmailAlreadyRegisteredException();
        }

        // Criptografa a senha
        log.info("Criptografando a senha para o e-mail: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
                .orElseThrow(() -> {
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
        userUpdated.setCellPhone(newUser.getCellPhone());
        userUpdated.setBirthDate(newUser.getBirthDate());

        // Atualiza o endereço do usuário
        if (newUser.getAddress() != null) {
            if (userUpdated.getAddress() == null) {
                log.info("Criando um novo endereço para o usuário com ID {}.", id);
                userUpdated.setAddress(enderecoUserMapper.toEntity(newUser.getAddress())); // Cria novo Endereço
            } else {
                log.info("Atualizando o endereço existente do usuário com ID {}.", id);
                UserAddress enderecoUpdated = userUpdated.getAddress();
                UserAddressDto novoEndereco = newUser.getAddress();

                enderecoUpdated.setCep(novoEndereco.getCep());
                enderecoUpdated.setStreet(novoEndereco.getStreet());
                enderecoUpdated.setNumber(novoEndereco.getNumber());
                enderecoUpdated.setAddressComplement(novoEndereco.getAddressComplement());
                enderecoUpdated.setNeighborhood(novoEndereco.getNeighborhood());
                enderecoUpdated.setCity(novoEndereco.getCity());
                enderecoUpdated.setUf(novoEndereco.getUf());
                enderecoUpdated.setRegion(novoEndereco.getRegion());
            }
        }
        userUpdated.setRoleEnum(newUser.getRoleEnum());

        // Salva o usuário atualizado
        userRepository.save(userUpdated);
        log.info("Usuário com ID {} atualizado com sucesso.", id);
    }
}