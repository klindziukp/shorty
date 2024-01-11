/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.controller;

import com.klindziuk.shorty.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Mono;

@Controller
public class LinkController {

  private final LinkService linkService;

  @Autowired
  public LinkController(LinkService linkService) {
    this.linkService = linkService;
  }

  @GetMapping("/{linkKey}")
  public Mono<RedirectView> redirect(@PathVariable String linkKey) {
    return linkService.redirectToLink(linkKey);
  }
}
