package com.laundry.main.whatsapp.validator;

import static org.assertj.core.api.Assertions.assertThat;

import com.laundry.main.whatsapp.config.WhatsAppConfigurationProperties;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

class WhatsAppSignatureVerifierTest {

    @Test
    void acceptsValidMetaSignatureAndRejectsChangedPayload() throws Exception {
        WhatsAppConfigurationProperties properties = new WhatsAppConfigurationProperties();
        properties.setAppSecret("test-app-secret");
        WhatsAppSignatureVerifier verifier = new WhatsAppSignatureVerifier(properties);
        String payload = "{\"object\":\"whatsapp_business_account\"}";
        String signature = sign(payload, properties.getAppSecret());

        assertThat(verifier.isValid(payload, signature)).isTrue();
        assertThat(verifier.isValid(payload + " ", signature)).isFalse();
    }

    private String sign(String payload, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return "sha256=" + HexFormat.of().formatHex(
                mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));
    }
}
