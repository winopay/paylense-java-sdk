package com.winopay.paylense.models;

public class PaylenseError {


  private int code;
  private String message;


  /**
   * Get Http Status Code.
   *
   * @return Http status Code
   */
  public int status() {
    return code;
  }

  /**
   * Get error Message.
   *
   * @return String
   */
  public String message() {
    return message;
  }

}
