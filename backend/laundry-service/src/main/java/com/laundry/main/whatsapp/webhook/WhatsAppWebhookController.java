package com.laundry.main.whatsapp.webhook;

import com.laundry.main.whatsapp.config.WhatsAppConfigurationProperties;
import com.laundry.main.whatsapp.dto.webhook.WhatsAppWebhookPayload;
import com.laundry.main.whatsapp.service.webhook.WhatsAppWebhookService;
import com.laundry.main.whatsapp.validator.WhatsAppSignatureVerifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/webhook/whatsapp")
@RequiredArgsConstructor
@Tag(name = "WhatsApp Webhook", description = "Meta webhook verification and event receiver")
public class WhatsAppWebhookController {

    private final WhatsAppConfigurationProperties properties;
    private final WhatsAppSignatureVerifier signatureVerifier;
    private final WhatsAppWebhookService webhookService;
    private final ObjectMapper objectMapper;

    @GetMapping
    @Operation(
            summary = "Verify the Meta webhook",
            description = "Public endpoint called by Meta while configuring the webhook subscription.",
            security = {})
    public ResponseEntity<String> verify(
            @RequestParam(name = "hub.mode") String mode,
            @RequestParam(name = "hub.verify_token") String verifyToken,
            @RequestParam(name = "hub.challenge") String challenge) {
        boolean verified = "subscribe".equals(mode)
                && StringUtils.hasText(properties.getVerifyToken())
                && properties.getVerifyToken().equals(verifyToken);
        return verified
                ? ResponseEntity.ok(challenge)
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    @Operation(
            summary = "Receive a Meta webhook event",
            description = "Validates X-Hub-Signature-256 before accepting messages and status events.",
            security = {})
    public ResponseEntity<Void> receive(
            @RequestHeader(name = "X-Hub-Signature-256", required = false) String signature,
            @RequestBody String rawBody) {
        if (!signatureVerifier.isValid(rawBody, signature)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            WhatsAppWebhookPayload payload = objectMapper.readValue(
                    rawBody, WhatsAppWebhookPayload.class);
            webhookService.process(payload);
            return ResponseEntity.ok().build();
        } catch (JacksonException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
