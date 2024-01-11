/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.service.impl;

import com.klindziuk.shorty.exception.InvalidLinkException;
import com.klindziuk.shorty.model.request.LinkRequest;
import com.klindziuk.shorty.service.ValidationService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

  private final UrlValidator urlValidator;

  public ValidationServiceImpl() {
    this.urlValidator = new UrlValidator();
  }

  @Override
  public void validateLink(LinkRequest linkRequest) {
    final String link = linkRequest.getLink();
    if (!urlValidator.isValid(link)) {
      throw new InvalidLinkException(link);
    }
  }
}
