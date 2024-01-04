/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller;

import com.klindziuk.shorty.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
public class LinkController {

  @Autowired private final LinkRepository linkRepository;

  @Autowired
  public LinkController(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  @GetMapping("/{linkKey}")
  public Mono<RedirectView> redirect(@PathVariable String linkKey) {
    return linkRepository
        .findByLinkKey(linkKey)
        .map(
            c -> {
              c.setClickCount(c.getClickCount() + 1);
              return new RedirectView(c.getLink());
            });
  }
}
