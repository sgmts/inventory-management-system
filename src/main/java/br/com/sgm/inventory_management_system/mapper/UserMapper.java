package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {EnderecoUserMapper.class})
public interface UserMapper {

    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "endereco", target = "endereco")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "localDateToString")
    @Mapping(source = "endereco", target = "endereco")
    UserDTO toDto(User user);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formatter);
    }

    @Named("localDateToString")
    default String localDateToString(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    // Método para mapeamento de Optional<User> para Optional<UserDTO>
    default Optional<UserDTO> toOptionalDto(Optional<User> optionalUser) {
        return optionalUser.map(this::toDto);
    }

    // Método para mapeamento de Optional<UserDTO> para Optional<User>
    default Optional<User> toOptionalEntity(Optional<UserDTO> optionalUserDTO) {
        return optionalUserDTO.map(this::toEntity);
    }

}