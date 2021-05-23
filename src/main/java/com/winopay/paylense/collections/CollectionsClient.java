package com.winopay.paylense.collections;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.winopay.paylense.models.RequestToPay;
import com.winopay.paylense.models.Transaction;
import com.winopay.paylense.BaseClient;
import com.winopay.paylense.RequestOptions;
import com.winopay.paylense.AuthorizationInterceptor;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CollectionsClient extends BaseClient {


  RequestOptions opts;
  Gson gson;
  private CollectionsService apiService;
  private OkHttpClient httpClient;
  private Retrofit retrofitClient;
  private Retrofit client;


  /**
   * InpaymentsClient.
   *
   * @param opts RequestOptions
   */
  public CollectionsClient(RequestOptions opts) {
    String url = "https://api.paylense.com/api/" + opts.getVersion();
    this.opts = opts;
    this.gson = getGson();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.


    okhttpbuilder.addInterceptor(new AuthorizationInterceptor(this.opts));
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(60, TimeUnit.SECONDS);


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

    this.apiService = this.retrofitClient.create(CollectionsService.class);


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
   * Request To Pay.
   *
   * @param mobile       String
   * @param amount       String
   * @param processingNumber   String
   * @param narration    String
   * @return String
   * @throws IOException when there is a network error
   */
  public String requestToPay(String mobile, String amount, String processingNumber, String narration) throws IOException {
    RequestToPay rbody = new RequestToPay(mobile, amount, processingNumber,
        narration);
    String ref = UUID.randomUUID().toString();
    this.apiService.requestToPay(rbody).execute();
    return ref;

  }

  /**
   * Request To Pay.
   *
   * @param opts HashMap
   * @return String
   * @throws IOException when there is a network error
   */
  public String requestToPay(HashMap<String, String> opts) throws IOException {
    RequestToPay rbody = new RequestToPay(opts.get("mobile"), opts.get("amount"),
        opts.get("processingNumber"), opts.get("narration"));
    String ref = UUID.randomUUID().toString();
    this.apiService.requestToPay(rbody).execute();
    return ref;

  }


}
