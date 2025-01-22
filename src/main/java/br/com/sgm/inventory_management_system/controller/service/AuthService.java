package br.com.sgm.inventory_management_system.controller.service;

import br.com.sgm.inventory_management_system.dto.LoginRequestDTO;

public interface AuthService {

    String authenticate( LoginRequestDTO loginRequest);
}