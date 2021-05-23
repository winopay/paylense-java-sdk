package com.winopay.paylense.disbursements;

import java.io.IOException;
import java.util.HashMap;

import com.winopay.paylense.PaylenseException;
import com.winopay.paylense.RequestOptions;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class LiveDisbursementsClientTest {

  /**
   * Test outpayments.
   *
   * @throws IOException when netowk error.
   */

  @Test
  public void testTransfer() throws IOException {


    RequestOptions opts = RequestOptions.builder()
        .build();


    HashMap<String, String> collMap = new HashMap<String, String>();
    collMap.put("amount", "100");
    collMap.put("msisdn", "0782123456");
    collMap.put("processingNumber", "ext123");
    collMap.put("narration", "testNarration");

    DisbursementsClient client = new DisbursementsClient(opts);

    try {
      String transactionRef = client.transfer(collMap);
      assertNotNull(transactionRef);

    } catch (PaylenseException e) {
      e.printStackTrace();
    }

  }

}
