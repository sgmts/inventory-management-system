package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.UserAddressDto;
import br.com.sgm.inventory_management_system.model.auth.UserAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {
    UserAddress toDTO(UserAddress userAddress);

    UserAddress toEntity(UserAddressDto userAddressDto);

}