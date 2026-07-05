package com.laundry.main.whatsapp.service.impl;

import com.laundry.main.whatsapp.client.WhatsAppClient;
import com.laundry.main.whatsapp.dto.SendTextMessageRequest;
import com.laundry.main.whatsapp.dto.SendTextMessageResponse;
import com.laundry.main.whatsapp.dto.SendMediaMessageRequest;
import com.laundry.main.whatsapp.dto.SendTemplateMessageRequest;
import com.laundry.main.whatsapp.dto.graph.request.WhatsAppGraphRequest;
import com.laundry.main.whatsapp.dto.graph.response.WhatsAppGraphResponse;
import com.laundry.main.whatsapp.enums.MediaType;
import com.laundry.main.whatsapp.enums.MessageType;
import com.laundry.main.whatsapp.enums.MessagingProduct;
import com.laundry.main.whatsapp.service.WhatsAppService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppServiceImpl implements WhatsAppService {

    private final WhatsAppClient whatsAppClient;

    @Override
    public SendTextMessageResponse sendTextMessage(
            SendTextMessageRequest request) {

        log.info("Preparing WhatsApp message for {}", request.getMobile());

        WhatsAppGraphRequest graphRequest = WhatsAppGraphRequest.builder()
                .messagingProduct(apiValue(MessagingProduct.WHATSAPP))
                .to(normalizeMobile(request.getMobile()))
                .type(apiValue(MessageType.TEXT))
                .text(
                        WhatsAppGraphRequest.Text.builder()
                                .body(request.getMessage())
                                .previewUrl(request.isPreviewUrl())
                                .build())
                .build();

        return toResponse(whatsAppClient.send(graphRequest));
    }

    @Override
    public SendTextMessageResponse sendTemplateMessage(SendTemplateMessageRequest request) {
        List<WhatsAppGraphRequest.Parameter> parameters = request.getBodyParameters().stream()
                .map(value -> WhatsAppGraphRequest.Parameter.builder()
                        .type("text")
                        .text(value)
                        .build())
                .toList();

        List<WhatsAppGraphRequest.Component> components = parameters.isEmpty()
                ? null
                : List.of(WhatsAppGraphRequest.Component.builder()
                        .type("body")
                        .parameters(parameters)
                        .build());

        WhatsAppGraphRequest graphRequest = WhatsAppGraphRequest.builder()
                .messagingProduct(apiValue(MessagingProduct.WHATSAPP))
                .to(normalizeMobile(request.getMobile()))
                .type(apiValue(MessageType.TEMPLATE))
                .template(WhatsAppGraphRequest.Template.builder()
                        .name(request.getTemplateName())
                        .language(WhatsAppGraphRequest.Language.builder()
                                .code(request.getLanguageCode())
                                .build())
                        .components(components)
                        .build())
                .build();

        return toResponse(whatsAppClient.send(graphRequest));
    }

    @Override
    public SendTextMessageResponse sendMediaMessage(SendMediaMessageRequest request) {
        WhatsAppGraphRequest.Media media = WhatsAppGraphRequest.Media.builder()
                .link(request.getLink())
                .caption(request.getCaption())
                .filename(request.getType() == MediaType.DOCUMENT ? request.getFilename() : null)
                .build();

        WhatsAppGraphRequest graphRequest = WhatsAppGraphRequest.builder()
                .messagingProduct(apiValue(MessagingProduct.WHATSAPP))
                .to(normalizeMobile(request.getMobile()))
                .type(apiValue(request.getType()))
                .image(request.getType() == MediaType.IMAGE ? media : null)
                .document(request.getType() == MediaType.DOCUMENT ? media : null)
                .build();

        return toResponse(whatsAppClient.send(graphRequest));
    }

    private SendTextMessageResponse toResponse(WhatsAppGraphResponse graphResponse) {

        String messageId = null;

        if (graphResponse != null
                && graphResponse.getMessages() != null
                && !graphResponse.getMessages().isEmpty()) {

            messageId = graphResponse
                    .getMessages()
                    .get(0)
                    .getId();
        }

        return SendTextMessageResponse.builder()
                .status("SUCCESS")
                .messageId(messageId)
                .build();
    }

    private String normalizeMobile(String mobile) {
        return mobile.startsWith("+") ? mobile.substring(1) : mobile;
    }

    private String apiValue(Enum<?> value) {
        return value.name().toLowerCase();
    }

}
