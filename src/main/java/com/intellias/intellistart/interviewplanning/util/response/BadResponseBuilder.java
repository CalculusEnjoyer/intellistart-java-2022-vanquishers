package com.intellias.intellistart.interviewplanning.util.response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Build methods for bad responses.
 */
public class BadResponseBuilder {

  /**
   * Bad response from given args.

   * @param status http status
   * @param code error code
   * @param message error message
   * @return response from object (like a JSON)
   */
  public static ResponseEntity<Object> makeResponse(HttpStatus status,
      String code, String message) {
    Map<String, String> jsonContent = new HashMap<>();
    jsonContent.put("errorCode", code);
    jsonContent.put("errorMessage", message);
    return new ResponseEntity<>(jsonContent, status);
  }

}
