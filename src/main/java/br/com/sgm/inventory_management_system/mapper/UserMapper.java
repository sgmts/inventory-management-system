package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {EnderecoUserMapper.class})
public interface UserMapper {

    @Mapping(source = "endereco", target = "endereco")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "endereco", target = "endereco")
    UserDTO toDto(User user);

    // Método para mapeamento de Optional<User> para Optional<UserDTO>
    default Optional<UserDTO> toOptionalDto(Optional<User> optionalUser) {
        return optionalUser.map(this::toDto);
    }

    // Método para mapeamento de Optional<UserDTO> para Optional<User>
    default Optional<User> toOptionalEntity(Optional<UserDTO> optionalUserDTO) {
        return optionalUserDTO.map(this::toEntity);
    }

}