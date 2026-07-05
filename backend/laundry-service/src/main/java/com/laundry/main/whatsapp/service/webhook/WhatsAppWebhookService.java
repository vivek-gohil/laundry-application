package com.laundry.main.whatsapp.service.webhook;

import com.laundry.main.whatsapp.dto.webhook.WhatsAppWebhookPayload;

public interface WhatsAppWebhookService {

    void process(WhatsAppWebhookPayload payload);
}
