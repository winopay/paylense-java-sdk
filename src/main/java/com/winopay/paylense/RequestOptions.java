package com.winopay.paylense;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RequestOptions {


  private final String appId;
  private final String username;
  private final String password;


  private final String targetEnvironment;
  private final String version;

  /**
   * Build options.
   *
   * @param username    String
   * @param password   String
   * @param appId       String
   * @param targetEnvironment      String
   * @param version               String
   */
  public RequestOptions(String username,
                        String password,
                        String appId,
                        String targetEnvironment,
                        String version) {

    this.username = username;
    this.password = password;
    this.appId = appId;
    
    this.targetEnvironment = targetEnvironment;
    this.version = version;


  }

  /**
   * Builder.
   *
   * @return Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * for playing nice with unittests.
   *
   * @return Builder
   */
  public Builder toBuilder() {
    return new Builder()
        .setPassword(this.password)
        .setUsername(this.username)
        .setAppId(this.appId)
        .setVersion(this.version)
        .setTargetEnvironment(this.targetEnvironment);

  }

  /**
   * get App Id.
   *
   * @return String
   */
  public String getAppId() {
    return this.appId;
  }

  /**
   * get API username.
   *
   * @return String
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Get API password.
   *
   * @return String
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Get Target Environment.
   *
   * @return String
   */
  public String getTargetEnvironment() {
    return this.targetEnvironment;
  }

  /**
   * get Currency.
   *
   * @return String
   */
  public String getVersion() {
    return this.version;
  }


  public static class Builder {

    private String appId;
    private String username;
    private String password;

    private String version = "v1";

    private String targetEnvironment = "sandbox";


    /**
     * Constructs a request options builder with the global parameters (API key, client ID and
     * API version) as default values.
     */
    public Builder() {
      this.appId = System.getenv("PAYHERE_APP_ID");
      this.username = System.getenv("PAYHERE_USERNAME");
      this.password = System.getenv("PAYHERE_PASSWORD");

    }


    /**
     * Normalize Keys.
     *
     * @param key String
     * @return String
     */
    private static String normalizeKey(String key) {

      String normalized = key.trim();
      if (normalized.isEmpty()) {
        throw new InvalidRequestOptionsException("Empty  key specified!");
      }
      return normalized;
    }

    /**
     * Get Inpayments primary key.
     *
     * @return String
     */
    public String getPassword() {
      return this.password;
    }

    /**
     * Set Inpayments Primary Key.
     *
     * @param password String
     * @return String
     */
    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    /**
     * get Inpayments User Id.
     *
     * @return String
     */
    public String getAppId() {
      return this.appId;
    }

    /**
     * Set Inpayments User Id.
     *
     * @param appId String
     * @return Builder
     */
    public Builder setAppId(String appId) {
      this.appId = appId;
      return this;
    }

    /**
     * get Inpayments Api Secret.
     *
     * @return String
     */
    public String getUsername() {
      return this.username;
    }

    /**
     * Set Inpayments Api Secret.
     *
     * @param username String
     * @return String
     */
    public Builder setUsername(String username) {
      this.username = username;
      return this;
    }

    /**
     * Get Target Environment.
     *
     * @return String
     */
    public String getTargetEnvironment() {
      return this.targetEnvironment;
    }

    /**
     * Set Target Environment.
     *
     * @param environment String
     * @return Builder
     */
    public Builder setTargetEnvironment(String environment) {
      this.targetEnvironment = environment;
      return this;
    }

    /**
     * Get Currency.
     *
     * @return String
     */
    public String getVersion() {
      return this.version;
    }

    /**
     * Set Currency.
     *
     * @param version String
     * @return Builder
     */
    public Builder setVersion(String version) {
      this.version = version;
      return this;
    }

    /**
     * RequestOptions.
     *
     * @return RequestOptions
     */
    public RequestOptions build() {
      return new RequestOptions(
          this.username,
          this.password,
          this.appId,
          this.targetEnvironment,
          this.version
      );

    }


  }

  public static class InvalidRequestOptionsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Ovveride.
     *
     * @param message String
     */
    public InvalidRequestOptionsException(String message) {
      super(message);
    }
  }


}



