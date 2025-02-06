package br.com.sgm.inventory_management_system.controller.cep;

import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;
import br.com.sgm.inventory_management_system.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CepController {

    @Autowired
    private CepService cepService;

    @GetMapping("/buscar-cep/{uf}/{city}/{street}")
    public ResponseEntity<List<ViaCepResponseDto>> buscarCep(
            @PathVariable String uf,
            @PathVariable String city,
            @PathVariable String street) {

        return ResponseEntity.status(HttpStatus.OK).body(cepService.findZipCode(uf, city, street));

    }
}