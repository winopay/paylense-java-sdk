package com.winopay.paylense.models;

import com.google.gson.annotations.SerializedName;

public class Transfer {
  @SerializedName("msisdn")
  private String msisdn;
  @SerializedName("narration")
  private String narration;
  @SerializedName("processingNumber")
  private String processingNumber;
  private String amount;

  /**
   * Request To Pay.
   *
   * @param msisdn       String
   * @param amount       Amount that will be debited from the msisdn account.
   * @param processingNumber   External id is used as a reference to the transaction.
   *                     External id is used for reconciliation.
   *                     The external id will be included in transaction history report.
   *                     External id is not required to be unique.
   * @param narration    Message that will be written in the payee transaction
   *                     history note field.
   */
  public Transfer(String mobile,
                      String amount,
                      String processingNumber,
                      String narration) {
    this.msisdn = mobile;
    this.amount = amount;
    this.processingNumber = processingNumber;
    this.narration = narration;
  }
}



