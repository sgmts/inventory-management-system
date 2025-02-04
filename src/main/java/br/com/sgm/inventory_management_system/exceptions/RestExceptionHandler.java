package br.com.sgm.inventory_management_system.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Credenciais inválidas";
    private static final String EMAIL_ALREADY_REGISTERED_MESSAGE = "E-mail já cadastrado no sistema";
    private static final String USER_NOT_FOUND_MESSAGE = "Usuário não encontrado";
    private static final String JWT_KEY_MISSING_MESSAGE = "A chave JWT é inválida ou está ausente";
    private static final String ERROR_DELETING_USER_MESSAGE = "Erro ao Deletar Usuario do Sistema";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Produto não encontrado";


    @ExceptionHandler(InvalidCredentialsException.class)
    private ResponseEntity<RestErrorMessage> invalidCredentialsExceptionHandler(InvalidCredentialsException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("4001", INVALID_CREDENTIALS_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyRegisteredExceptionExceptionHandler(EmailAlreadyRegisteredException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("5001", EMAIL_ALREADY_REGISTERED_MESSAGE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundExceptionExceptionHandler(UserNotFoundException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("5002", USER_NOT_FOUND_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(JwtKeyMissingException.class)
    private ResponseEntity<RestErrorMessage> jwtKeyMissingExceptionExceptionHandler(JwtKeyMissingException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("6001", JWT_KEY_MISSING_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(ErrorDeletingUserException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingUserExceptionHandler(ErrorDeletingUserException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("5003", ERROR_DELETING_USER_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("erro", ex.getLocalizedMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(IOException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("erro", ex.getLocalizedMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundExceptionExceptionHandler(ProductNotFoundException e) {
        RestErrorMessage threatResponse = new RestErrorMessage("7001", PRODUCT_NOT_FOUND_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}