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
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(name = "com.klindziuk.shorty.cache.enabled", havingValue = "true")
public class LinkServiceCacheImpl extends AbstractLinkService {

  private static final String KEY = "link";

  private final ReactiveHashOperations<String, Long, LinkResponse> hashOperations;
  private final LinkRepository linkRepository;
  private final ValidationService validationService;

  @Autowired
  public LinkServiceCacheImpl(
      ShortyConfig shortyConfig,
      ReactiveHashOperations<String, Long, LinkResponse> hashOperations,
      LinkRepository linkRepository,
      ValidationService validationService) {
    super(shortyConfig);
    this.shortyConfig = shortyConfig;
    this.hashOperations = hashOperations;
    this.linkRepository = linkRepository;
    this.validationService = validationService;
  }

  @Override
  public Flux<LinkResponse> getAllLinks() {
    return linkRepository.findAll().map(this::toLinkResponse);
  }

  @Override
  public Mono<LinkResponse> getLink(Long id) {
    return hashOperations.get(KEY, id).switchIfEmpty(this.getFromDatabaseAndCache(id));
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
        .map(
            linkEntity -> {
              final LinkResponse linkResponse = toLinkResponse(linkEntity);
              this.hashOperations
                  .put(KEY, linkEntity.getId(), linkResponse)
                  .thenReturn(linkResponse);
              return linkResponse;
            });
  }

  @Override
  public Mono<Void> updateLink(Long id, LinkRequest linkRequest) {
    validationService.validateLink(linkRequest);
    return this.linkRepository
        .findById(id)
        .flatMap(
            link -> {
              link.setLink(linkRequest.getLink());
              return linkRepository.save(link);
            })
        .then(hashOperations.remove(KEY, id))
        .then();
  }

  @Override
  public Mono<RedirectView> redirectToLink(String linkKey) {
    return linkRepository
        .findByLinkKey(linkKey)
        .switchIfEmpty(Mono.error(new LinkNotFoundException(linkKey)))
        .doOnNext(c -> hashOperations.remove(KEY, c.getId()))
        .flatMap(
            link -> {
              link.setClickCount(link.getClickCount() + 1);
              hashOperations.remove(KEY, link.getId());
              return linkRepository
                  .save(link)
                  .map(updatedLink -> new RedirectView(updatedLink.getLink()));
            });
  }

  @Override
  public Mono<Void> deleteLink(Long id) {
    return this.linkRepository.deleteById(id).then(hashOperations.remove(KEY, id)).then();
  }

  private Mono<LinkResponse> getFromDatabaseAndCache(Long id) {
    return linkRepository
        .findById(id)
        .map(this::toLinkResponse)
        .flatMap(link -> this.hashOperations.put(KEY, id, link).thenReturn(link));
  }
}
