package br.com.sgm.inventory_management_system.exceptions;

public class ErrorDeletingCategoryException extends RuntimeException {
    public ErrorDeletingCategoryException(String message) {
        super(message);
    }
  public ErrorDeletingCategoryException() {
  }
}
