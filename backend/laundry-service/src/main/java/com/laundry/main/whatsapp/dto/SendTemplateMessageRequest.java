package com.laundry.main.whatsapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Approved WhatsApp template message request")
public class SendTemplateMessageRequest {

    @Schema(description = "Recipient number including country code", example = "+919876543210")
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Mobile number must include the country code")
    private String mobile;

    @Schema(description = "Approved template name", example = "order_ready")
    @NotBlank(message = "Template name is required")
    private String templateName;

    @Schema(description = "Template language code", example = "en_US")
    @NotBlank(message = "Language code is required")
    private String languageCode;

    @Schema(
            description = "Values for the template body placeholders, in order",
            example = "[\"Vivek\", \"ORD-202607010001\"]")
    private List<String> bodyParameters = List.of();
}
