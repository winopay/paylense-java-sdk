package com.winopay.paylense.disbursements;


import com.winopay.paylense.models.Transaction;
import com.winopay.paylense.models.Transfer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DisbursementsService {

  /**
   * transfer request.
   *
   * @param body Transfer
   * @param ref  String
   * @return Void
   */
  @POST("/outpayments")
  @Headers("Content-Type: application/json")
  Call<Void> transfer(@Body Transfer body);

  /**
   * getTransactionStatus.
   *
   * @param transactionId String
   * @return Transaction
   */
  @GET("/outpayments/{transactionId}")
  @Headers("Content-Type: application/json")
  Call<Transaction> getTransactionStatus(@Path("transactionId") String transactionId);

}
