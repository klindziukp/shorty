/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.exception;

public class LinkAlreadyExistsException extends RuntimeException {

  public LinkAlreadyExistsException(String link) {
    super(String.format("Link [ %s ] already exists", link));
  }
}
