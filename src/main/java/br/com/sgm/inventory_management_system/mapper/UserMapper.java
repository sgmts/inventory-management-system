package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.UserDTO;
import br.com.sgm.inventory_management_system.model.auth.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public User mapDTOToModel(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO mapModelToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public Optional<UserDTO> mapModelToDTOOptional(Optional<User> patient) {
        return Optional.ofNullable(modelMapper.map(patient, UserDTO.class));
    }
}