package com.laundry.main.whatsapp.dto;

import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@Schema(description = "WhatsApp message submission response")
public class SendTextMessageResponse {
    @Schema(example = "SUCCESS")
    private String status;

    @Schema(example = "wamid.HBgMOTE5ODc2NTQzMjEwFQIAERgS...")
    private String messageId;

}
