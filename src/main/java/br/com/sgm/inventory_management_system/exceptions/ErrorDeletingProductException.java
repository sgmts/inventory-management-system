package br.com.sgm.inventory_management_system.exceptions;

public class ErrorDeletingProductException extends RuntimeException {
    public ErrorDeletingProductException(String message) {
        super(message);
    }
  public ErrorDeletingProductException() {
  }
}
