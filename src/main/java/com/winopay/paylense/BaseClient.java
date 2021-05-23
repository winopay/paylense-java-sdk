package com.winopay.paylense;

import com.winopay.paylense.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class BaseClient {


  /**
   * getGson.
   *
   * @return Gson
   */
  public Gson getGson() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();
  }


  /**
   * create Retrofit.
   *
   * @param apiEndpoint  String
   * @param okHttpClient OkHttpClient
   * @return Retrofit
   */
  Retrofit createRetrofit(final String apiEndpoint,
                          final @NonNull OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }


  /**
   * get Http Logging Interceptor.
   *
   * @return HttpLoggingInterceptor
   */
  HttpLoggingInterceptor getHttpLoggingInterceptor() {
    final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return interceptor;
  }

  /**
   * Get Api Retrofit.
   *
   * @param apiEndpoint  String
   * @param okHttpClient OkHttpClient
   * @return Retrofit
   */
  Retrofit getApiRetrofit(final @NonNull String apiEndpoint,
                          final @NonNull OkHttpClient okHttpClient) {
    return createRetrofit(apiEndpoint, okHttpClient);
  }


}