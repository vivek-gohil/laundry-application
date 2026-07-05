package com.laundry.main.whatsapp.event;

import com.laundry.main.whatsapp.dto.webhook.WhatsAppWebhookPayload;

public record WhatsAppWebhookReceivedEvent(WhatsAppWebhookPayload payload) {}
