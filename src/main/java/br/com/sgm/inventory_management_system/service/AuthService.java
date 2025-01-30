package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.LoginRequestDto;

public interface AuthService {

    String authenticate( LoginRequestDto loginRequest);
}