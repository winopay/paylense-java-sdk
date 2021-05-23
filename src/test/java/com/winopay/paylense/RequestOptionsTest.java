package com.winopay.paylense;

import com.winopay.paylense.RequestOptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestOptionsTest {

  @Test
  public void testPersistentValuesInToBuilder() {
    RequestOptions.Builder opts = RequestOptions.builder()
        .setAppId("11011")
        .setUsername("sdk")
        .setPassword("sdk@2020")
        .setVersion("v1")
        .setTargetEnvironment("sandbox")
        .build().toBuilder();


    // assuming these are stable across a given stripe integration

    assertEquals("11011", opts.getAppId());
    assertEquals("sdk", opts.getUsername());
    assertEquals("sdk@2020", opts.getPassword());
    assertEquals("v1", opts.getVersion());
    assertEquals("sandbox", opts.getTargetEnvironment());


  }

}
