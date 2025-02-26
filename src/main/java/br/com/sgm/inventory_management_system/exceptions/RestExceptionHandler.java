package br.com.sgm.inventory_management_system.exceptions;

import br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Map;

import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.CATEGORY_NOT_FOUND_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.DUPLICATE_ENTRY_SNIPPET;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.EMAIL_ALREADY_REGISTERED_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_CATEGORY_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_PRODUCT_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_DELETING_USER_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_FOREIGN_KEY_VIOLATION_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_FOREIGN_KEY_VIOLATION_GENERIC_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_LOG_ENTITY_CHANGES_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_VALUE_NOT_VALID_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ERROR_VALUE_NOT_VALID_DESERIALIZE_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.INVALID_CREDENTIALS_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.INVALID_DESERIALIZATION_SNIPPET;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.INVALID_ZIP_CODE_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.JWT_KEY_MISSING_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.PRODUCT_NOT_FOUND_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.QUERY_ERROR_MESSAGE_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.USER_NOT_FOUND_CODE;
import static br.com.sgm.inventory_management_system.exceptions.constants.ErrorConstants.ZIP_CODE_NOT_FOUND_CODE;

@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<RestErrorMessage> buildErrorResponse(String errorCode, HttpStatus status) {
        Map.Entry<String, String> errorEntry = ErrorConstants.getError(errorCode);
        return ResponseEntity.status(status).body(new RestErrorMessage(errorEntry.getKey(), errorEntry.getValue()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    private ResponseEntity<RestErrorMessage> invalidCredentialsExceptionHandler(InvalidCredentialsException e) {
        return buildErrorResponse(INVALID_CREDENTIALS_CODE, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyRegisteredExceptionExceptionHandler(EmailAlreadyRegisteredException e) {
        return buildErrorResponse(EMAIL_ALREADY_REGISTERED_CODE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundExceptionExceptionHandler(UserNotFoundException e) {
        return buildErrorResponse(USER_NOT_FOUND_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtKeyMissingException.class)
    private ResponseEntity<RestErrorMessage> jwtKeyMissingExceptionExceptionHandler(JwtKeyMissingException e) {
        return buildErrorResponse(JWT_KEY_MISSING_CODE, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorDeletingUserException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingUserExceptionHandler(ErrorDeletingUserException e) {
        return buildErrorResponse(ERROR_DELETING_USER_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            threatResponse.setCode(ERROR_VALUE_NOT_VALID_CODE);
            threatResponse.setMessage(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<RestErrorMessage> invalidFormatExceptionHandler(InvalidFormatException ex) {
        if (ex.getLocalizedMessage().contains(INVALID_DESERIALIZATION_SNIPPET)) {
            return buildErrorResponse(ERROR_VALUE_NOT_VALID_DESERIALIZE_CODE, HttpStatus.BAD_REQUEST);
        }
        return buildErrorResponse(ERROR_VALUE_NOT_VALID_CODE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<RestErrorMessage> ioExceptionHandler(IOException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage("erro", ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundExceptionExceptionHandler(ProductNotFoundException e) {
        return buildErrorResponse(PRODUCT_NOT_FOUND_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorDeletingProductException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingUserExceptionHandler(ErrorDeletingProductException e) {
        return buildErrorResponse(ERROR_DELETING_PRODUCT_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidZipCodeException.class)
    public ResponseEntity<RestErrorMessage> invalidZipCodeExceptionHandler(InvalidZipCodeException e) {
        return buildErrorResponse(INVALID_ZIP_CODE_CODE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ZipCodeNotFoundException.class)
    public ResponseEntity<RestErrorMessage> zipCodeNotFoundExceptionHandler(ZipCodeNotFoundException e) {
        return buildErrorResponse(ZIP_CODE_NOT_FOUND_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CepQueryErrorException.class)
    public ResponseEntity<RestErrorMessage> cepQueryErrorExceptionHandler(CepQueryErrorException e) {
        return buildErrorResponse(QUERY_ERROR_MESSAGE_CODE, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<RestErrorMessage> categoryNotFoundExceptionExceptionHandler(CategoryNotFoundException e) {
        return buildErrorResponse(CATEGORY_NOT_FOUND_CODE, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ErrorDeletingCategoryException.class)
    private ResponseEntity<RestErrorMessage> errorDeletingCategoryExceptionHandler(ErrorDeletingCategoryException e) {
        return buildErrorResponse(ERROR_DELETING_CATEGORY_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<RestErrorMessage> foreignKeyViolationHandler(DataIntegrityViolationException e) {

        if (e.getMessage().contains(DUPLICATE_ENTRY_SNIPPET)) {
            return buildErrorResponse(ERROR_FOREIGN_KEY_VIOLATION_CODE, HttpStatus.CONFLICT);
        }
        return buildErrorResponse(ERROR_FOREIGN_KEY_VIOLATION_GENERIC_CODE, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ErrorLogEntityChangesException.class)
    private ResponseEntity<RestErrorMessage> errorLogEntityChangesExceptionHandler(ErrorLogEntityChangesException e) {
        return buildErrorResponse(ERROR_LOG_ENTITY_CHANGES_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}