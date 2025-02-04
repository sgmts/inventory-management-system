package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {UserAddressMapper.class})
public interface UserMapper {

    @Mapping(source = "address", target = "address")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "address", target = "address")
    UserDTO toDto(User user);

    default Optional<UserDTO> toOptionalDto(Optional<User> optionalUser) {
        return optionalUser.map(this::toDto);
    }
}