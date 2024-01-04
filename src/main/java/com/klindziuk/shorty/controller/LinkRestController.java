/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller;

import com.klindziuk.shorty.model.LinkEntity;
import com.klindziuk.shorty.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class LinkRestController {

  @Autowired private final LinkRepository linkRepository;

  @Autowired
  public LinkRestController(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  @GetMapping("/links")
  public Flux<LinkEntity> getAllLinks() {
    return linkRepository.findAll();
  }
}
