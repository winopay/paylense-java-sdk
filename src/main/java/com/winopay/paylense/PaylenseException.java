package com.winopay.paylense;

import java.io.IOException;

/**
 * An exception class for the response.
 */
public final class PaylenseException extends IOException {


  /**
   * PaylenseException.
   *
   * @param response String
   */
  public PaylenseException(String response) {
    super(response);

  }
}
