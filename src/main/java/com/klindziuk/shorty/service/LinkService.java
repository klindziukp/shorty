/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty.service;

import com.klindziuk.shorty.model.request.LinkRequest;
import com.klindziuk.shorty.model.response.LinkResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.RedirectView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface LinkService {

  Flux<LinkResponse> getAllLinks();

  Mono<LinkResponse> getLink(Long id);

  Mono<LinkResponse> addLink(LinkRequest linkRequest);

  Mono<Void> updateLink(Long id, LinkRequest linkRequest);

  Mono<RedirectView> redirectToLink(String linkKey);

  Mono<Void> deleteLink(Long id);
}
