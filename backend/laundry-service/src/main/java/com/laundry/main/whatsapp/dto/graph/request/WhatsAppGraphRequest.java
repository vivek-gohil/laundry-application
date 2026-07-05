package com.laundry.main.whatsapp.dto.graph.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhatsAppGraphRequest {

    @JsonProperty("messaging_product")
    private final String messagingProduct;
    private final String to;
    private final String type;
    private final Text text;
    private final Template template;

    private final Media image;

    private final Media document;

    @Getter
    @Builder
    public static class Text {

        private final String body;

        @JsonProperty("preview_url")
        private final Boolean previewUrl;
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Media {
        private final String link;
        private final String caption;
        private final String filename;
    }

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Template {
        private final String name;
        private final Language language;
        private final List<Component> components;
    }

    @Getter
    @Builder
    public static class Language {
        private final String code;
    }

    @Getter
    @Builder
    public static class Component {
        private final String type;
        private final List<Parameter> parameters;
    }

    @Getter
    @Builder
    public static class Parameter {
        private final String type;
        private final String text;
    }
}
