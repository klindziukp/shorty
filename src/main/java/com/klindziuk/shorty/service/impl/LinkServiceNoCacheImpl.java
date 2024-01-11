/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.service.impl;

import com.klindziuk.shorty.config.ShortyConfig;
import com.klindziuk.shorty.exception.LinkAlreadyExistsException;
import com.klindziuk.shorty.exception.LinkNotFoundException;
import com.klindziuk.shorty.model.request.LinkRequest;
import com.klindziuk.shorty.model.response.LinkResponse;
import com.klindziuk.shorty.repository.LinkRepository;
import com.klindziuk.shorty.service.ValidationService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(name = "com.klindziuk.shorty.cache.enabled", havingValue = "false")
public class LinkServiceNoCacheImpl extends AbstractLinkService {

  @Autowired
  public LinkServiceNoCacheImpl(
      ShortyConfig shortyConfig,
      LinkRepository linkRepository,
      ValidationService validationService) {
    super(shortyConfig);
    this.linkRepository = linkRepository;
    this.validationService = validationService;
  }

  @Override
  public Flux<LinkResponse> getAllLinks() {
    return linkRepository.findAll().map(this::toLinkResponse);
  }

  @Override
  public Mono<LinkResponse> getLink(Long id) {
    return linkRepository.findById(id).map(this::toLinkResponse);
  }

  @Override
  public Mono<LinkResponse> addLink(LinkRequest linkRequest) {
    validationService.validateLink(linkRequest);
    return linkRepository
        .findByLink(linkRequest.getLink())
        .map(
            linkEntity -> {
              if (Objects.nonNull(linkEntity)) {
                throw new LinkAlreadyExistsException(linkEntity.getLink());
              }
              return linkEntity;
            })
        .switchIfEmpty(linkRepository.save(toLinkEntity(linkRequest)))
        .map(this::toLinkResponse);
  }

  @Override
  public Mono<Void> updateLink(Long id, LinkRequest linkRequest) {
    validationService.validateLink(linkRequest);
    return this.linkRepository
        .findById(id)
        .flatMap(
            c -> {
              c.setLink(linkRequest.getLink());
              return linkRepository.save(c);
            })
        .then();
  }

  @Override
  public Mono<RedirectView> redirectToLink(String linkKey) {
    return linkRepository
        .findByLinkKey(linkKey)
        .switchIfEmpty(Mono.error(new LinkNotFoundException(linkKey)))
        .flatMap(
            link -> {
              link.setClickCount(link.getClickCount() + 1);
              return linkRepository
                  .save(link)
                  .map(updatedLink -> new RedirectView(updatedLink.getLink()));
            });
  }

  @Override
  public Mono<Void> deleteLink(Long id) {
    return this.linkRepository.deleteById(id);
  }
}
