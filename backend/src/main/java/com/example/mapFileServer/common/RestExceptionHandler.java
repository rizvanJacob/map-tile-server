package com.example.mapFileServer.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex){
      log.trace("IllegalArgumentException", ex);
      return ResponseEntity
              .badRequest()
              .body(Map.of("message", ex.getMessage()));
    }
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException ex){
        log.trace("IllegalArgumentException", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }
    public ResponseEntity<Map<String, String>> handleInternalServerError(IllegalArgumentException ex){
        log.trace("IllegalArgumentException", ex);
        return ResponseEntity
                .internalServerError()
                .body(Map.of("message", ex.getMessage()));
    }
}
