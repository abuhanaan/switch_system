package com.example.demo.exceptions;

public class TimeoutException extends ApiException {

  public TimeoutException(String message) {
    super(message);
  }
}
