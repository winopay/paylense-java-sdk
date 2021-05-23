package com.winopay.paylense;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.winopay.paylense.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizationInterceptor implements Interceptor {

  Logger logger;
  private Retrofit retrofitClient;
  private RequestOptions opts;

  /**
   * AuthorizationInterceptor.
   *
   * @param opts    RequestOptions
   */
  public AuthorizationInterceptor(RequestOptions opts) {

    this.opts = opts;
    this.logger = Logger.getLogger(AuthorizationInterceptor.class.getName());

    final Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.
    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    okhttpbuilder.addInterceptor(httpLoggingInterceptor);


    okhttpbuilder.connectTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(60, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(60, TimeUnit.SECONDS);


    OkHttpClient httpClient = okhttpbuilder
        .build();


    Retrofit retrofitClient = new Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    this.retrofitClient = retrofitClient;
  }


  /**
   * request wrapper.
   *
   * @param initialRequest Request
   * @return Request
   */
  private Request request(final Request initialRequest) {

    this.logger.log(Level.INFO, "Using token >>>>>>>>>>>>>>>>> ");


    String credentials = Credentials.basic(this.opts.getUsername(), this.opts.getPassword());

    return initialRequest.newBuilder()
        //.header("Accept", "application/json")
        .addHeader("Authorization", credentials)

        .method(initialRequest.method(), initialRequest.body())
        .build();
  }


  /**
   * Intercept.
   *
   * @param chain Chain
   * @return Response
   * @throws IOException when there is a network error
   */
  @Override
  public okhttp3.Response intercept(Chain chain) throws IOException {


    okhttp3.Response mainResponse = chain.proceed(request(chain.request()));


    Request mainRequest = chain.request();


    // if response code is 401 or 403, 'mainRequest' has encountered authentication error
    if (mainResponse.code() == 401 || mainResponse.code() == 403) {


      this.logger.log(Level.INFO, "<<<<<<<<<<<<<<<Getting Fresh Token");


      String credentials = Credentials.basic(this.opts.getUsername(), this.opts.getPassword());
      
              // retry the 'mainRequest' which encountered an authentication error
        // add new token into 'mainRequest' header and request again
        Request.Builder builder = mainRequest.newBuilder()
            .addHeader("Authorization", credentials)
            .method(mainRequest.method(), mainRequest.body());
        mainResponse = chain.proceed(builder.build());

    } else if (mainResponse.code() == 400 || mainResponse.code() == 500
        || mainResponse.code() == 404) {
      String error = "";

      try {
        error = mainResponse.body().string();
      } catch (IllegalStateException e) {
        this.logger.log(Level.SEVERE, e.toString());

      }


      throw new PaylenseException(error);


    } else {

      Integer numRequests = 0;

      while (numRequests < 3) {

        okhttp3.Response r = chain.proceed(chain.request());
        if (r.isSuccessful()) {
          return r;


        }

        numRequests++;
      }

    }


    return mainResponse;


  }

}
