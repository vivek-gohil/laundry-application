package com.laundry.main.common.constants;

public final class SecurityConstants {

  // Preferred names
  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String BEARER_PREFIX = "Bearer ";

  // Backwards-compatible aliases
  public static final String HEADER = AUTHORIZATION_HEADER;
  public static final String TOKEN_PREFIX = BEARER_PREFIX;

  public static final long JWT_EXPIRATION = 3600000L; // 1 hour

  private SecurityConstants() {}
}
