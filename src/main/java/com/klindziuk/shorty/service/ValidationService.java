/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.service;

import com.klindziuk.shorty.model.request.LinkRequest;

public interface ValidationService {

  void validateLink(LinkRequest linkRequest);
}
