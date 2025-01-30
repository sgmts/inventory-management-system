package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.EnderecoUserDto;
import br.com.sgm.inventory_management_system.model.auth.EnderecoUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoUserMapper {
    EnderecoUserDto toDTO(EnderecoUser enderecoUser);
    EnderecoUser toEntity(EnderecoUserDto enderecoUserDto);

}