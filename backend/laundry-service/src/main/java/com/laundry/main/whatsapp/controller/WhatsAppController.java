package com.laundry.main.whatsapp.controller;

import com.laundry.main.whatsapp.dto.SendMediaMessageRequest;
import com.laundry.main.whatsapp.dto.SendTemplateMessageRequest;
import com.laundry.main.whatsapp.dto.SendTextMessageRequest;
import com.laundry.main.whatsapp.dto.SendTextMessageResponse;
import com.laundry.main.whatsapp.service.WhatsAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/whatsapp")
@RequiredArgsConstructor
@Tag(name = "WhatsApp", description = "Send messages through Meta WhatsApp Cloud API")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @PostMapping("/messages/text")
    @Operation(
            summary = "Send a text message",
            description = "Sends a free-form WhatsApp text message. Requires a valid JWT access token.")
    public ResponseEntity<SendTextMessageResponse> sendTextMessage(
            @Valid @RequestBody SendTextMessageRequest request) {
        return ResponseEntity.ok(whatsAppService.sendTextMessage(request));
    }

    @PostMapping("/messages/templates")
    @Operation(
            summary = "Send a template message",
            description = "Sends an approved WhatsApp template with optional body parameters.")
    public ResponseEntity<SendTextMessageResponse> sendTemplateMessage(
            @Valid @RequestBody SendTemplateMessageRequest request) {
        return ResponseEntity.ok(whatsAppService.sendTemplateMessage(request));
    }

    @PostMapping("/messages/media")
    @Operation(
            summary = "Send an image or document",
            description = "Sends media from a publicly accessible HTTPS URL.")
    public ResponseEntity<SendTextMessageResponse> sendMediaMessage(
            @Valid @RequestBody SendMediaMessageRequest request) {
        return ResponseEntity.ok(whatsAppService.sendMediaMessage(request));
    }
}
