package com.laundry.main.whatsapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "WhatsApp text message request")
public class SendTextMessageRequest {

    @Schema(description = "Recipient number including country code", example = "+919876543210")
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Mobile number must include the country code")
    private String mobile;

    @Schema(description = "Text message body", example = "Your laundry order is ready for delivery.")
    @NotBlank(message = "Message is required")
    @Size(max = 4096, message = "Message must not exceed 4096 characters")
    private String message;

    @Schema(description = "Generate a preview for URLs in the message", example = "false")
    private boolean previewUrl;

}
