package com.winopay.paylense.collections;

import java.io.IOException;
import java.util.HashMap;

import com.winopay.paylense.PaylenseException;
import com.winopay.paylense.RequestOptions;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class LiveCollectionsClientTest {

  /**
   * Test request to pay.
   *
   * @throws IOException when network error
   */

  @Test
  public void testRequestToPay() throws IOException {

    RequestOptions opts = RequestOptions.builder()
        .build();
    assertNotNull(opts.getAppId());
    assertNotNull(opts.getUsername());
    assertNotNull(opts.getPassword());


    HashMap<String, String> collMap = new HashMap<String, String>();
    collMap.put("amount", "100");
    collMap.put("msisdn", "0782123456");
    collMap.put("processingNumber", "ext123");
    collMap.put("narration", "testNarration");

    CollectionsClient client = new CollectionsClient(opts);

    try {
      String transactionRef = client.requestToPay(collMap);
      assertNotNull(transactionRef);

    } catch (PaylenseException e) {
      e.printStackTrace();
    }


  }


}
