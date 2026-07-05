package com.laundry.main.whatsapp.validator;

import com.laundry.main.whatsapp.config.WhatsAppConfigurationProperties;
import com.laundry.main.whatsapp.exception.WhatsAppException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class WhatsAppSignatureVerifier {

    private static final String SIGNATURE_PREFIX = "sha256=";
    private static final String HMAC_SHA_256 = "HmacSHA256";

    private final WhatsAppConfigurationProperties properties;

    public boolean isValid(String rawBody, String signatureHeader) {
        if (!StringUtils.hasText(properties.getAppSecret())) {
            throw new WhatsAppException(
                    "WhatsApp webhook signing is not configured. Set WHATSAPP_APP_SECRET.");
        }
        if (!StringUtils.hasText(signatureHeader)
                || !signatureHeader.startsWith(SIGNATURE_PREFIX)) {
            return false;
        }

        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(new SecretKeySpec(
                    properties.getAppSecret().getBytes(StandardCharsets.UTF_8), HMAC_SHA_256));
            byte[] expected = mac.doFinal(rawBody.getBytes(StandardCharsets.UTF_8));
            byte[] supplied = HexFormat.of().parseHex(
                    signatureHeader.substring(SIGNATURE_PREFIX.length()));
            return MessageDigest.isEqual(expected, supplied);
        } catch (IllegalArgumentException ex) {
            return false;
        } catch (Exception ex) {
            throw new WhatsAppException("Unable to validate WhatsApp webhook signature.", ex);
        }
    }
}
