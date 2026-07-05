package com.laundry.main.whatsapp.service;

import com.laundry.main.whatsapp.dto.SendTextMessageRequest;
import com.laundry.main.whatsapp.dto.SendTextMessageResponse;
import com.laundry.main.whatsapp.dto.SendMediaMessageRequest;
import com.laundry.main.whatsapp.dto.SendTemplateMessageRequest;

public interface WhatsAppService {

    SendTextMessageResponse sendTextMessage(SendTextMessageRequest request);

    SendTextMessageResponse sendTemplateMessage(SendTemplateMessageRequest request);

    SendTextMessageResponse sendMediaMessage(SendMediaMessageRequest request);

}
