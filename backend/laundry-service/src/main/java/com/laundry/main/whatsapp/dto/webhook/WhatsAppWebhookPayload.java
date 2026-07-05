package com.laundry.main.whatsapp.dto.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WhatsAppWebhookPayload(
        String object,
        @JsonProperty("entry") List<Entry> entries) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Entry(String id, List<Change> changes) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Change(String field, Value value) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Value(
            @JsonProperty("messaging_product") String messagingProduct,
            Metadata metadata,
            List<Contact> contacts,
            List<Message> messages,
            List<Status> statuses) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Metadata(
            @JsonProperty("display_phone_number") String displayPhoneNumber,
            @JsonProperty("phone_number_id") String phoneNumberId) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Contact(Profile profile, @JsonProperty("wa_id") String waId) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Profile(String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Message(
            String from,
            String id,
            String timestamp,
            String type,
            Text text,
            JsonNode image,
            JsonNode document,
            JsonNode interactive,
            JsonNode button) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Text(String body) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Status(
            String id,
            String status,
            String timestamp,
            @JsonProperty("recipient_id") String recipientId,
            List<JsonNode> errors) {}
}
