package com.winopay.paylense.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Transaction {

  private float amount;
  @SerializedName("processingNumber")
  private String processingNumber;
  private String msisdn;
  private String status;
  private Reason reason;


  /**
   * Get Transaction Status.
   *
   * @return String
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * Get Transaction amount.
   *
   * @return String amount
   */
  public float getAmount() {
    return this.amount;
  }

  /**
   * Get External Ref.
   *
   * @return String
   */

  public String getProcessingNumber() {
    return this.processingNumber;
  }

  @Override
  public String toString() {
    Gson gson = new Gson();

    return gson.toJson(this);
  }

  class Reason {

    private String code;
    private String message;
  }
}
