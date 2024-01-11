/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.model;

import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class ErrorEntity {

  private HttpStatus httpStatus;
  private Integer httpStatusCode;
  private String path;
  private String errorMessage;
  private Instant timeStamp;
}
