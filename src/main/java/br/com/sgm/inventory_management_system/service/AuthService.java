package br.com.sgm.inventory_management_system.service;

import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;

public interface AuthService {

    String authenticate( LoginRequestDTO loginRequest);
}