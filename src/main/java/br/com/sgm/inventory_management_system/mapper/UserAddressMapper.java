package br.com.sgm.inventory_management_system.mapper;

import br.com.sgm.inventory_management_system.dto.user.UserAddressDto;
import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;
import br.com.sgm.inventory_management_system.model.auth.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {
    UserAddressDto toDTO(UserAddress userAddress);

    UserAddress toEntity(UserAddressDto userAddressDto);

    // Ignora campos com valor null (não sobrescreve os valores existentes no objeto de destino).
    @Mapping(source = "cep", target = "cep", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "logradouro", target = "street", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "bairro", target = "neighborhood", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "localidade", target = "city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "uf", target = "uf", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "regiao", target = "region", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserAddressFromViaCep(ViaCepResponseDto viaCepResponseDto, @MappingTarget UserAddressDto userAddressDto);

}