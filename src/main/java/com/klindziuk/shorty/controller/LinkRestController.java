/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller;

import com.klindziuk.shorty.model.request.LinkRequest;
import com.klindziuk.shorty.model.response.LinkResponse;
import com.klindziuk.shorty.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class LinkRestController {

  private final LinkService linkService;

  @Autowired
  public LinkRestController(LinkService linkService) {
    this.linkService = linkService;
  }

  @GetMapping("api/v1/links")
  public Flux<LinkResponse> getAllLinks() {
    return linkService.getAllLinks();
  }

  @GetMapping("api/v1/{id}")
  public Mono<LinkResponse> getLink(@PathVariable Long id) {
    return linkService.getLink(id);
  }

  @PostMapping("api/v1/add")
  public Mono<LinkResponse> addLink(@RequestBody LinkRequest linkRequest) {
    return linkService.addLink(linkRequest);
  }

  @PostMapping("api/v1/update/{id}")
  public Mono<Void> update(@PathVariable Long id, @RequestBody LinkRequest linkRequest) {
    return linkService.updateLink(id, linkRequest);
  }

  @DeleteMapping("api/v1/delete/{id}")
  public Mono<Void> delete(@PathVariable Long id) {
    return linkService.deleteLink(id);
  }
}
