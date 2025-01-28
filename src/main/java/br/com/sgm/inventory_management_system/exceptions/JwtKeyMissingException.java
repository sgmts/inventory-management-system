package br.com.sgm.inventory_management_system.exceptions;


public class JwtKeyMissingException extends RuntimeException {
    public JwtKeyMissingException(String message) {
        super(message);
    }
    public JwtKeyMissingException() {}
}
