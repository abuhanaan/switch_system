package com.example.demo.exceptions;

public class BadRequestException extends ApiException {

  public BadRequestException(String message) {
    super(message);
  }
}
