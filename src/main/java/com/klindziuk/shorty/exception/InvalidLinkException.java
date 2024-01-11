/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.exception;

public class InvalidLinkException extends RuntimeException {

  public InvalidLinkException(String link) {
    super(String.format("Link [ %s ] is invalid", link));
  }
}
