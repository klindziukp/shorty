/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.model.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("links")
public class LinkEntity {

  @Id
  @Column("link_id")
  private Long id;

  @Column("link_key")
  private String linkKey;

  @Column("link")
  private String link;

  @Column("click_count")
  private Long clickCount;
}
