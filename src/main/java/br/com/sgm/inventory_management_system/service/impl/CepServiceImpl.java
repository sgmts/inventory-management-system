package br.com.sgm.inventory_management_system.service.impl;

import br.com.sgm.inventory_management_system.dto.user.ViaCepResponseDto;
import br.com.sgm.inventory_management_system.exceptions.CepQueryErrorException;
import br.com.sgm.inventory_management_system.exceptions.InvalidZipCodeException;
import br.com.sgm.inventory_management_system.exceptions.ZipCodeNotFoundException;
import br.com.sgm.inventory_management_system.service.CepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CepServiceImpl implements CepService {

    @Value("${api.viacep.endpoint.consulta.endereco}")
    private String viaCepUrlEndereco;

    @Value("${api.viacep.endpoint.consulta.cep}")
    private String viaCepUrlCEp;

    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-?\\d{3}");
    private static final String EXPLICIT_ERROR_RESPONSE = "true";


    @Autowired
    private RestTemplate restTemplate;

    public ViaCepResponseDto findAddress(String cep) {

        if (!isZipCodeValid(cep)) {
            log.error("Falha na validação: O CEP {} não está no formato correto", cep);
            throw new InvalidZipCodeException();
        }

        try {
            log.info("Consultando a API ViaCEP para o CEP {} na URL: {}", cep, viaCepUrlEndereco);
            // Monta a URL da requisição
            String url = String.format(viaCepUrlEndereco, cep);

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

    public List<ViaCepResponseDto> findZipCode(String uf, String city, String street) {

        try {
            // Monta a URL da requisição
            String url = String.format(viaCepUrlCEp, uf, city, street);

            // Faz a requisição para a API ViaCEP e espera uma lista de respostas
            List<ViaCepResponseDto> response = restTemplate.exchange(
                    url, // URL da API
                    HttpMethod.GET, // Método HTTP
                    null,  // Requisição (caso não precise enviar nada, use `null`)
                    new ParameterizedTypeReference<List<ViaCepResponseDto>>() { // Tipo esperado
                    }
            ).getBody(); // Retorna o corpo da resposta

            return response;
        } catch (ZipCodeNotFoundException e) {
            log.error("Erro ao buscar o CEP na API ViaCEP: {}", e.getMessage(), e);
            // Re-lança a exceção, para ser tratada no nível superior
            throw e;

        } catch (Exception e) {
            log.error("Erro ao buscar o CEP  na API ViaCEP: {}", e.getMessage(), e);
            throw new CepQueryErrorException();
        }
    }
}
