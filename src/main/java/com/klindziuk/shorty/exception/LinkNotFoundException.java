/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.exception;

public class LinkNotFoundException extends RuntimeException {

  public LinkNotFoundException(String linkKey) {
    super(String.format("Link not found for [ %s ] link key", linkKey));
  }
}
