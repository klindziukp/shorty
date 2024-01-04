/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class ShortyApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShortyApplication.class, args);
  }
}
