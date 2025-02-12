package br.com.sgm.inventory_management_system.exceptions.constants;

import java.util.AbstractMap;
import java.util.Map;

public class ErrorConstants {

    public static final String ERRO_DEFAULT_MESSAGE = "Erro Interno!";
    public static final String ERRO_DEFAULT_CODE = "0000";

    public static final String INVALID_CREDENTIALS_MESSAGE = "Credenciais inválidas";
    public static final String INVALID_CREDENTIALS_CODE = "4001";

    public static final String JWT_KEY_MISSING_MESSAGE = "A chave JWT é inválida ou está ausente";
    public static final String JWT_KEY_MISSING_CODE = "4002";


    public static final String EMAIL_ALREADY_REGISTERED_MESSAGE = "E-mail já cadastrado no sistema";
    public static final String EMAIL_ALREADY_REGISTERED_CODE = "5001";

    public static final String USER_NOT_FOUND_MESSAGE = "Usuário não encontrado";
    public static final String USER_NOT_FOUND_CODE = "5002";

    public static final String ERROR_DELETING_USER_MESSAGE = "Erro ao Deletar Usuario do Sistema";
    public static final String ERROR_DELETING_USER_CODE = "5003";


    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Produto não encontrado";
    public static final String PRODUCT_NOT_FOUND_CODE = "6001";


    public static final String ERROR_DELETING_PRODUCT_MESSAGE = "Erro ao Deletar produto do Sistema";
    public static final String ERROR_DELETING_PRODUCT_CODE = "6002";

    public static final String INVALID_ZIP_CODE_MESSAGE = "CEP inválido. O formato esperado é '00000-000' ou '00000000";
    public static final String INVALID_ZIP_CODE_CODE = "7001";

    public static final String ZIP_CODE_NOT_FOUND_MESSAGE = "CEP não encontrado";
    public static final String ZIP_CODE_NOT_FOUND_CODE = "7002";

    public static final String QUERY_ERROR_MESSAGE = "Erro ao buscar CEP para este endereço.";
    public static final String QUERY_ERROR_MESSAGE_CODE = "7003";

    public static final String CATEGORY_NOT_FOUND_CODE = "8001";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Categoria não encontrada";

    public static final String ERROR_DELETING_CATEGORY_CODE = "8002";
    public static final String ERROR_DELETING_CATEGORY_MESSAGE = "Erro ao Deletar categoria do Sistema";

    public static final String ERROR_JWT_CODE = "1000";

    private static final Map<String, String> ERROR_MAP;

    static {
        ERROR_MAP = Map.ofEntries(
                new AbstractMap.SimpleEntry<>(ERRO_DEFAULT_MESSAGE, ERRO_DEFAULT_CODE),
                new AbstractMap.SimpleEntry<>(INVALID_CREDENTIALS_CODE, INVALID_CREDENTIALS_MESSAGE),
                new AbstractMap.SimpleEntry<>(EMAIL_ALREADY_REGISTERED_CODE, EMAIL_ALREADY_REGISTERED_MESSAGE),
                new AbstractMap.SimpleEntry<>(USER_NOT_FOUND_CODE, USER_NOT_FOUND_MESSAGE),
                new AbstractMap.SimpleEntry<>(JWT_KEY_MISSING_CODE, JWT_KEY_MISSING_MESSAGE),
                new AbstractMap.SimpleEntry<>(ERROR_DELETING_USER_CODE, ERROR_DELETING_USER_MESSAGE),
                new AbstractMap.SimpleEntry<>(INVALID_ZIP_CODE_MESSAGE, INVALID_ZIP_CODE_CODE),
                new AbstractMap.SimpleEntry<>(ZIP_CODE_NOT_FOUND_MESSAGE, ZIP_CODE_NOT_FOUND_CODE),
                new AbstractMap.SimpleEntry<>(QUERY_ERROR_MESSAGE_CODE, QUERY_ERROR_MESSAGE),
                new AbstractMap.SimpleEntry<>(CATEGORY_NOT_FOUND_CODE, CATEGORY_NOT_FOUND_MESSAGE),
                new AbstractMap.SimpleEntry<>(ERROR_DELETING_CATEGORY_CODE, ERROR_DELETING_CATEGORY_MESSAGE)
        );
    }

    private ErrorConstants() {
        // Construtor privado para evitar instância da classe
    }

    public static String getErrorMessage(String code) {
        return ERROR_MAP.getOrDefault(code, "Erro desconhecido");
    }
}