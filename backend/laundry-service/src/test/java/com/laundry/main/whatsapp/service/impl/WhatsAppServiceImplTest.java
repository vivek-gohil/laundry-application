package com.laundry.main.whatsapp.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.laundry.main.whatsapp.client.WhatsAppClient;
import com.laundry.main.whatsapp.dto.SendTextMessageRequest;
import com.laundry.main.whatsapp.dto.SendTextMessageResponse;
import com.laundry.main.whatsapp.dto.graph.request.WhatsAppGraphRequest;
import com.laundry.main.whatsapp.dto.graph.response.WhatsAppGraphResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WhatsAppServiceImplTest {

    @Mock private WhatsAppClient whatsAppClient;
    @Mock private WhatsAppGraphResponse graphResponse;
    @Mock private WhatsAppGraphResponse.Message graphMessage;
    @InjectMocks private WhatsAppServiceImpl whatsAppService;

    @Test
    void sendTextMessageBuildsGraphPayloadAndReturnsMessageId() {
        SendTextMessageRequest request = new SendTextMessageRequest();
        request.setMobile("+919876543210");
        request.setMessage("Your laundry is ready.");
        request.setPreviewUrl(false);

        when(graphMessage.getId()).thenReturn("wamid.123");
        when(graphResponse.getMessages()).thenReturn(List.of(graphMessage));
        when(whatsAppClient.send(any(WhatsAppGraphRequest.class))).thenReturn(graphResponse);

        SendTextMessageResponse response = whatsAppService.sendTextMessage(request);

        ArgumentCaptor<WhatsAppGraphRequest> captor =
                ArgumentCaptor.forClass(WhatsAppGraphRequest.class);
        org.mockito.Mockito.verify(whatsAppClient).send(captor.capture());
        WhatsAppGraphRequest graphRequest = captor.getValue();

        assertThat(graphRequest.getMessagingProduct()).isEqualTo("whatsapp");
        assertThat(graphRequest.getType()).isEqualTo("text");
        assertThat(graphRequest.getTo()).isEqualTo("919876543210");
        assertThat(graphRequest.getText().getBody()).isEqualTo("Your laundry is ready.");
        assertThat(response.getStatus()).isEqualTo("SUCCESS");
        assertThat(response.getMessageId()).isEqualTo("wamid.123");
    }
}
