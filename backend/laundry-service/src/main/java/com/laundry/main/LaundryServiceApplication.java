package com.laundry.main;

import com.laundry.main.auth.config.JwtProperties;
import com.laundry.main.whatsapp.config.WhatsAppConfigurationProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        JwtProperties.class,
        WhatsAppConfigurationProperties.class
})
public class LaundryServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LaundryServiceApplication.class, args);
  }
}
