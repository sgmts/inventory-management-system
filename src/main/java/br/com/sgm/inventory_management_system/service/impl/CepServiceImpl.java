package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;
import br.com.sgm.inventory_management_system.exceptions.InvalidZipCodeException;
import br.com.sgm.inventory_management_system.exceptions.ZipCodeNotFoundException;
import br.com.sgm.inventory_management_system.exceptions.CepQueryErrorException;
import br.com.sgm.inventory_management_system.service.CepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

@Slf4j
@Service
public class CepServiceImpl implements CepService {

    @Value("${api.viacep.url}")
    private String viaCepUrl;

    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-?\\d{3}");
    private static final String EXPLICIT_ERROR_RESPONSE = "true";


    @Autowired
    private RestTemplate restTemplate;

    public ViaCepResponseDto findZipCode(String cep) {

        if (!isZipCodeValid(cep)) {
            log.error("Falha na validação: O CEP {} não está no formato correto", cep);
            throw new InvalidZipCodeException();
        }

        try {
            log.info("Consultando a API ViaCEP para o CEP {} na URL: {}", cep, viaCepUrl);
            // Monta a URL da requisição
            String url = String.format(viaCepUrl, cep);

            // Faz a requisição para a API ViaCEP
            ViaCepResponseDto response = restTemplate.getForObject(url, ViaCepResponseDto.class);

            // Verifica se a resposta é nula ou se a API retornou erro explícito
            if (response == null || EXPLICIT_ERROR_RESPONSE.equals(response.getErro()) || response.getCep() == null) {
                log.warn("CEP {} não encontrado na API ViaCEP", cep);
                throw new ZipCodeNotFoundException();
            }

            return response;
        } catch (ZipCodeNotFoundException e) {
            log.error("Erro ao consultar o CEP {} na API ViaCEP: {}", cep, e.getMessage(), e);
            // Re-lança a exceção, para ser tratada no nível superior
            throw e;

        } catch (Exception e) {
            log.error("Erro ao consultar o CEP {} na API ViaCEP: {}", cep, e.getMessage(), e);
            throw new CepQueryErrorException();
        }
    }

    private boolean isZipCodeValid(String cep) {
        return CEP_PATTERN.matcher(cep).matches();
    }
}
