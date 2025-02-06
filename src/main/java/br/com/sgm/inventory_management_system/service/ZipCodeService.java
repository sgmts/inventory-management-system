package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;

import java.util.List;

public interface ZipCodeService {

    ViaCepResponseDto findAddress(String cep);

    List<ViaCepResponseDto> findZipCode(String uf, String cidade, String rua);
}