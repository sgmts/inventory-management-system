package br.com.sgm.inventory_management_system.exceptions;


public class ErrorDeletingUserException extends RuntimeException {
    public ErrorDeletingUserException(String message) {
        super(message);
    }
    public ErrorDeletingUserException() {}
}
