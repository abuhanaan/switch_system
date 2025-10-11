package com.example.demo.validator;

import com.example.demo.exceptions.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class InputValidator {

  public static void validate(BindingResult bindingResult)
      throws BadRequestException {
    if (!bindingResult.hasErrors()) {
      return;
    }
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

    // Format: field - message (one per line)
    String message = fieldErrors.stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining("; "));

    throw new BadRequestException("Errors: [" + message + "]");
  }
}
