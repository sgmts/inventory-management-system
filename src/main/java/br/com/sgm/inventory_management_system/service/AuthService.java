package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.login.LoginRequestDto;

public interface AuthService {

    String authenticate( LoginRequestDto loginRequest);
}