package com.laundry.main.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {

  /** Secret key used to sign JWT. */
  private String secretKey;

  /** Expiration time in milliseconds. */
  private Long expiration;
}
