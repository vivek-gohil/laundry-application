package com.laundry.main.whatsapp.service.webhook;

import com.laundry.main.whatsapp.dto.webhook.WhatsAppWebhookPayload;
import com.laundry.main.whatsapp.event.WhatsAppWebhookReceivedEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppWebhookServiceImpl implements WhatsAppWebhookService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void process(WhatsAppWebhookPayload payload) {
        int messageCount = values(payload).stream()
                .map(WhatsAppWebhookPayload.Value::messages)
                .filter(messages -> messages != null)
                .mapToInt(List::size)
                .sum();
        int statusCount = values(payload).stream()
                .map(WhatsAppWebhookPayload.Value::statuses)
                .filter(statuses -> statuses != null)
                .mapToInt(List::size)
                .sum();

        log.info("Received WhatsApp webhook. Messages={}, Statuses={}", messageCount, statusCount);
        eventPublisher.publishEvent(new WhatsAppWebhookReceivedEvent(payload));
    }

    private List<WhatsAppWebhookPayload.Value> values(WhatsAppWebhookPayload payload) {
        if (payload == null || payload.entries() == null) {
            return List.of();
        }
        return payload.entries().stream()
                .filter(entry -> entry.changes() != null)
                .flatMap(entry -> entry.changes().stream())
                .map(WhatsAppWebhookPayload.Change::value)
                .filter(value -> value != null)
                .toList();
    }
}
