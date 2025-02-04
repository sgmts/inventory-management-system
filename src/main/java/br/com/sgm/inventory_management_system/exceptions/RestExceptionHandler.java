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

import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.EMAIL_ALREADY_REGISTERED_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.EMAIL_ALREADY_REGISTERED_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_PRODUCT_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_PRODUCT_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_USER_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_USER_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.INVALID_CREDENTIALS_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.INVALID_CREDENTIALS_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.JWT_KEY_MISSING_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.JWT_KEY_MISSING_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.PRODUCT_NOT_FOUND_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.PRODUCT_NOT_FOUND_MESSAGE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.USER_NOT_FOUND_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.USER_NOT_FOUND_MESSAGE;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    private ResponseEntity<RestErrorMessage> invalidCredentialsExceptionHandler(InvalidCredentialsException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(INVALID_CREDENTIALS_CODE, INVALID_CREDENTIALS_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyRegisteredExceptionExceptionHandler(EmailAlreadyRegisteredException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(EMAIL_ALREADY_REGISTERED_CODE, EMAIL_ALREADY_REGISTERED_MESSAGE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundExceptionExceptionHandler(UserNotFoundException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(USER_NOT_FOUND_CODE, USER_NOT_FOUND_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(JwtKeyMissingException.class)
    private ResponseEntity<RestErrorMessage> jwtKeyMissingExceptionExceptionHandler(JwtKeyMissingException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(JWT_KEY_MISSING_CODE, JWT_KEY_MISSING_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(ErrorDeletingUserException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingUserExceptionHandler(ErrorDeletingUserException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(ERROR_DELETING_USER_CODE, ERROR_DELETING_USER_MESSAGE);
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
        RestErrorMessage threatResponse = new RestErrorMessage(PRODUCT_NOT_FOUND_CODE, PRODUCT_NOT_FOUND_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ErrorDeletingProductException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingUserExceptionHandler(ErrorDeletingProductException e) {
        RestErrorMessage threatResponse = new RestErrorMessage(ERROR_DELETING_PRODUCT_CODE, ERROR_DELETING_PRODUCT_MESSAGE);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}