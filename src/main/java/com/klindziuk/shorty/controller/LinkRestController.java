/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller;

import com.klindziuk.shorty.model.LinkEntity;
import com.klindziuk.shorty.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

  @PostMapping("/add")
  public Mono<LinkEntity> addLink(LinkEntity linkEntity) {
    return linkRepository.save(linkEntity);
  }

  @DeleteMapping("/add")
  public Mono<Void> addLink(Long linkId) {
    return linkRepository.deleteById(linkId);
  }
}
