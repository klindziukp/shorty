/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.service.impl;

import com.klindziuk.shorty.config.ShortyConfig;
import com.klindziuk.shorty.model.repository.LinkEntity;
import com.klindziuk.shorty.model.request.LinkRequest;
import com.klindziuk.shorty.model.response.LinkResponse;
import com.klindziuk.shorty.repository.LinkRepository;
import com.klindziuk.shorty.service.LinkService;
import com.klindziuk.shorty.service.ValidationService;
import org.apache.commons.lang3.RandomStringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractLinkService implements LinkService {

  protected ShortyConfig shortyConfig;
  protected LinkRepository linkRepository;
  protected ValidationService validationService;

  public AbstractLinkService(ShortyConfig shortyConfig) {
    this.shortyConfig = shortyConfig;
  }

  @Override
  public abstract Flux<LinkResponse> getAllLinks();

  @Override
  public abstract Mono<LinkResponse> addLink(LinkRequest linkRequest);

  @Override
  public abstract Mono<Void> updateLink(Long id, LinkRequest linkRequest);

  LinkEntity toLinkEntity(LinkRequest linkRequest) {
    return new LinkEntity()
        .setClickCount(0L)
        .setLink(linkRequest.getLink())
        .setLinkKey(RandomStringUtils.randomAlphanumeric(6));
  }

  LinkResponse toLinkResponse(LinkEntity linkEntity) {
    return new LinkResponse()
        .setId(linkEntity.getId())
        .setShortLink(shortyConfig.getServiceUrl() + linkEntity.getLinkKey())
        .setLink(linkEntity.getLink())
        .setClickCount(linkEntity.getClickCount());
  }
}
