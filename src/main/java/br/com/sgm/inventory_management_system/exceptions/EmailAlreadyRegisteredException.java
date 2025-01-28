package br.com.sgm.inventory_management_system.exceptions;


public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
    public EmailAlreadyRegisteredException() {}
}
