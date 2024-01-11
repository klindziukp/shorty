/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.shorty;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ShortySimulationJava extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .doNotTrackHeader("1")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");

  Iterator<Map<String, Object>> feeder =
      Stream.generate((Supplier<Map<String, Object>>) () -> {
            String link =
                "https://medium.com/dandelion-tutorials/building-native-image-with-spring-boot-"
                    + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            return Collections.singletonMap("link", link);
          }
      ).iterator();

  ScenarioBuilder getScenario = scenario("Get Link Simulation")
      .exec(http("Get First Link").get("/api/v1/1").check(status().is(200))).pause(1, 2)
      .exec(http("Get Second Link").get("/api/v1/2").check(status().is(200))).pause(1, 2)
      .exec(http("Get Third Link").get("/api/v1/3").check(status().is(200))).pause(1, 2);

  ScenarioBuilder addScenario = scenario("Add Link Simulation")
      .feed(feeder)
      .exec(http("Add Link").post("/api/v1/add")
      .header("Content-Type", "application/json")
      .body(StringBody("{\"link\": \"${link}\"}")).asJson()).pause(2, 3);
  {
    setUp(
        getScenario.injectOpen(constantUsersPerSec(100).during(100)).protocols(httpProtocol),
        addScenario.injectOpen(constantUsersPerSec(20).during(100)).protocols(httpProtocol)
    ).protocols(httpProtocol);
  }
}