package com.winopay.paylense;

import java.io.IOException;
import java.util.HashMap;

import com.winopay.paylense.collections.CollectionsClient;

import org.apache.commons.cli.ParseException;


public class Paylense {


  Paylense() {


  }

  /**
   * Provision Sandbox Account.
   *
   * @param args providerCallBackHost and primaryKey(Ocp-Apim-Subscription-Key)
   * @throws ParseException when args are missing
   * @throws IOException    when network error occurs
   */
  public static void main(String[] args) throws ParseException, IOException {


    // Make a request to pay call
    RequestOptions opts = RequestOptions.builder()
        .build();

    HashMap<String, String> collMap = new HashMap<String, String>();
    collMap.put("amount", "100");
    collMap.put("msisdn", "0782123456");
    collMap.put("ProcessingNumber", "ext123");
    collMap.put("narration", "testNarration");

    CollectionsClient client = new CollectionsClient(opts);

    try {
      String transactionRef = client.requestToPay(collMap);
      System.out.println(transactionRef);
    } catch (PaylenseException e) {
      e.printStackTrace();
    }


  }

}
