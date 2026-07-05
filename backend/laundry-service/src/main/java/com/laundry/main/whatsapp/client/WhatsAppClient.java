package com.laundry.main.whatsapp.client;

import com.laundry.main.whatsapp.config.WhatsAppConfigurationProperties;
import com.laundry.main.whatsapp.dto.graph.request.WhatsAppGraphRequest;
import com.laundry.main.whatsapp.dto.graph.response.WhatsAppGraphResponse;
import com.laundry.main.whatsapp.exception.WhatsAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class WhatsAppClient {

    private static final String GRAPH_API_PATH = "/%s/%s/messages";

    private final RestClient restClient;
    private final WhatsAppConfigurationProperties properties;

    /**
     * Sends a message to Meta Graph API.
     *
     * @param request Graph API request
     * @return Graph API response
     */
    public WhatsAppGraphResponse send(WhatsAppGraphRequest request) {

        validateConfiguration();
        String url = buildMessagePath();

        log.info("Sending WhatsApp message. To={}, Type={}",
                request.getTo(),
                request.getType());

        try {

            WhatsAppGraphResponse response = restClient
                    .post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION,
                            "Bearer " + properties.getAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(WhatsAppGraphResponse.class);

            log.info("WhatsApp message sent successfully.");

            return response;

        } catch (RestClientResponseException ex) {

            log.error("Graph API Error [{}] : {}",
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString());

            throw new WhatsAppException(
                    "Unable to send WhatsApp message.",
                    ex);

        } catch (Exception ex) {

            log.error("Unexpected error while calling WhatsApp Graph API", ex);

            throw new WhatsAppException(
                    "Unexpected error while sending WhatsApp message.",
                    ex);
        }

    }

    /**
     * Builds Meta Graph API URL.
     */
    private String buildMessagePath() {
        return String.format(
                        GRAPH_API_PATH,
                        properties.getApiVersion(),
                        properties.getPhoneNumberId());

    }

    private void validateConfiguration() {
        if (!StringUtils.hasText(properties.getPhoneNumberId())
                || !StringUtils.hasText(properties.getAccessToken())) {
            throw new WhatsAppException(
                    "WhatsApp API is not configured. Set WHATSAPP_PHONE_NUMBER_ID and WHATSAPP_ACCESS_TOKEN.");
        }

    }

}
