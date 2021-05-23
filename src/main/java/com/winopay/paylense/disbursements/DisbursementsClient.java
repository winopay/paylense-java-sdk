package com.winopay.paylense.disbursements;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.winopay.paylense.models.Transaction;
import com.winopay.paylense.models.Transfer;
import com.winopay.paylense.RequestOptions;
import com.winopay.paylense.utils.DateTimeTypeConverter;
import com.winopay.paylense.AuthorizationInterceptor;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DisbursementsClient {
  RequestOptions opts;
  Gson gson;
  private DisbursementsService apiService;
  private OkHttpClient httpClient;
  private Retrofit retrofitClient;

  /**
   * OutpaymentsClient.
   *
   * @param opts RequestOptions
   */
  public DisbursementsClient(RequestOptions opts) {
    String url = "https://api.paylense.com/api/" + opts.getVersion();
    this.opts = opts;
    this.gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.


    okhttpbuilder.addInterceptor(new AuthorizationInterceptor(this.opts));
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


    this.httpClient = okhttpbuilder
        .build();

    if(opts.getTargetEnvironment() == "sandbox"){
      url = "https://api-sandbox.paylense.com/api/" + opts.getVersion();
    }

    this.retrofitClient = new Retrofit.Builder()
        .client(this.httpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();

    this.apiService = this.retrofitClient.create(DisbursementsService.class);


  }


  /**
   * get Transaction.
   *
   * @param ref String
   * @return Transaction
   * @throws IOException when there is a network error
   */

  public Transaction getTransaction(String ref) throws IOException {
    Response<Transaction> transaction = this.apiService
        .getTransactionStatus(ref).execute();
    return transaction.body();

  }

  /**
   * transfer money.
   *
   * @param mobile       String
   * @param amount       String
   * @param processingNumber   String
   * @param narration    String
   * @return String
   * @throws IOException when there is a network error
   */
  public String transfer(String mobile, String amount,
                         String processingNumber, String narration) throws IOException {
    Transfer rbody = new Transfer(mobile, amount, processingNumber, narration);
    String ref = UUID.randomUUID().toString();
    this.apiService.transfer(rbody).execute();
    return ref;

  }

  /**
   * transfer money.
   *
   * @param opts HashMap
   * @return String
   * @throws IOException when there is a network error
   */
  public String transfer(HashMap<String, String> opts) throws IOException {
    Transfer rbody = new Transfer(opts.get("mobile"), opts.get("amount"),
        opts.get("processingNumber"), opts.get("narration"));
    String ref = UUID.randomUUID().toString();
    this.apiService.transfer(rbody).execute();
    return ref;

  }

}
