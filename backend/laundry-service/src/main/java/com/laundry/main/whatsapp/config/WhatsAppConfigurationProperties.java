package com.laundry.main.whatsapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.whatsapp")
public class WhatsAppConfigurationProperties {

    /**
     * https://graph.facebook.com
     */
    private String graphBaseUrl = "https://graph.facebook.com";

    /**
     * v23.0
     */
    private String apiVersion = "v23.0";

    /**
     * Phone Number ID
     */
    private String phoneNumberId;

    /**
     * Meta Access Token
     */
    private String accessToken;

    /**
     * Webhook Verify Token
     */
    private String verifyToken;

    /**
     * Meta application secret used to validate X-Hub-Signature-256.
     */
    private String appSecret;

}
