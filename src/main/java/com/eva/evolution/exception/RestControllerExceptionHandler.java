package com.eva.evolution.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<ExceptionDetails> handleWebExchangeBindException(WebExchangeBindException e, ServerWebExchange we) {
    ServerHttpRequest request = we.getRequest();
    var errors = e.getBindingResult()
        .getAllErrors()
        .stream()
        .map(el -> "field " + el.getObjectName() + " - " + el.getDefaultMessage())
        .collect(Collectors.toSet());
    var details = new ExceptionDetails(
        request.getPath().value(),
        e.getStatusCode().value(),
        Optional.ofNullable(e.getReason()).orElse(HttpStatus.BAD_REQUEST.getReasonPhrase()),
        errors.toString(),
        request.getId());
    return ResponseEntity.status(e.getStatusCode()).body(details);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ExceptionDetails> handleDbExceptions(DuplicateKeyException e, ServerWebExchange we) {
    ServerHttpRequest request = we.getRequest();
    var details = new ExceptionDetails(
        request.getPath().value(),
        HttpStatus.LOCKED.value(),
        Optional.ofNullable(e.getLocalizedMessage()).orElse(HttpStatus.LOCKED.getReasonPhrase()),
        "Ресурс уже зарегистрирован",
        request.getId());
    return ResponseEntity.status(HttpStatus.LOCKED.value()).body(details);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ExceptionDetails> handleDbExceptions(DataAccessException e, ServerWebExchange we) {
    ServerHttpRequest request = we.getRequest();
    var details = new ExceptionDetails(
        request.getPath().value(),
        HttpStatus.BAD_REQUEST.value(),
        Optional.ofNullable(e.getLocalizedMessage()).orElse(HttpStatus.BAD_REQUEST.getReasonPhrase()),
        Optional.ofNullable(e.getRootCause()).map(Throwable::getMessage).orElse(e.getLocalizedMessage()),
        request.getId());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(details);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDetails> handleDbExceptions(Exception e, ServerWebExchange we) {
    ServerHttpRequest request = we.getRequest();
    var details = new ExceptionDetails(
        request.getPath().value(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Что-то пошло не так",
        e.getLocalizedMessage(),
        request.getId());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(details);
  }
  public record ExceptionDetails(String path, int status, String error, String message, String requestId) {}
}
