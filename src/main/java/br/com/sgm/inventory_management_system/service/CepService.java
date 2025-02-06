package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;

public interface CepService {

    ViaCepResponseDto findZipCode(String cep);
}