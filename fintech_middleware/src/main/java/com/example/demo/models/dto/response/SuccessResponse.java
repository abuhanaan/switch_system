package com.example.demo.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse extends ApiResponse{

  private boolean status;
  private String message;
  private Object data;

  public static SuccessResponse buildSuccess(String message, Object data){
    return SuccessResponse.builder()
        .status(true)
        .message(message)
        .data(data)
        .build();
  }
}
