package com.example.demo.exceptions;

public class NotFoundException extends ApiException {

  public NotFoundException(String message) {
    super(message);
  }
}
