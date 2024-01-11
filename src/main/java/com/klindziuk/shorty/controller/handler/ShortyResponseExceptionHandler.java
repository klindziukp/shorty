/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller.handler;

import com.klindziuk.shorty.exception.InvalidLinkException;
import com.klindziuk.shorty.exception.LinkAlreadyExistsException;
import com.klindziuk.shorty.model.ErrorEntity;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
public class ShortyResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(LinkAlreadyExistsException.class)
  protected ResponseEntity<ErrorEntity> handleLinkAlreadyExists(
      LinkAlreadyExistsException ex, ServerWebExchange exchange) {
    ErrorEntity errorEntity =
        new ErrorEntity()
            .setHttpStatus(HttpStatus.BAD_REQUEST)
            .setPath(exchange.getRequest().getPath().value())
            .setHttpStatusCode(HttpStatus.BAD_REQUEST.value())
            .setErrorMessage(ex.getMessage())
            .setTimeStamp(Instant.now());
    return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidLinkException.class)
  protected ResponseEntity<ErrorEntity> handleInvalidLink(
      InvalidLinkException ex, ServerWebExchange exchange) {
    ErrorEntity errorEntity =
        new ErrorEntity()
            .setHttpStatus(HttpStatus.BAD_REQUEST)
            .setPath(exchange.getRequest().getPath().value())
            .setHttpStatusCode(HttpStatus.BAD_REQUEST.value())
            .setErrorMessage(ex.getMessage())
            .setTimeStamp(Instant.now());
    return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
  }
}
