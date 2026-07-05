package com.laundry.main.whatsapp.dto.graph.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhatsAppGraphResponse {

    @JsonProperty("messaging_product")
    private String messagingProduct;

    private List<Contact> contacts;

    private List<Message> messages;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Contact {

        private String input;

        @JsonProperty("wa_id")
        private String waId;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        private String id;
    }
}