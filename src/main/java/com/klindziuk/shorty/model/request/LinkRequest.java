/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LinkRequest {

  @NotEmpty private String link;
}
