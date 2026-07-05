package com.laundry.main.whatsapp.dto;

import com.laundry.main.whatsapp.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Schema(description = "WhatsApp image or document message request")
public class SendMediaMessageRequest {

    @Schema(description = "Recipient number including country code", example = "+919876543210")
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Mobile number must include the country code")
    private String mobile;

    @Schema(description = "Supported media type", example = "IMAGE")
    @NotNull(message = "Media type is required")
    private MediaType type;

    @Schema(
            description = "Publicly accessible HTTPS media URL",
            example = "https://example.com/laundry/order-ready.jpg")
    @NotBlank(message = "Media URL is required")
    @URL(protocol = "https", message = "Media URL must be a valid HTTPS URL")
    private String link;

    @Schema(description = "Optional media caption", example = "Your laundry order is ready.")
    @Size(max = 1024, message = "Caption must not exceed 1024 characters")
    private String caption;

    @Schema(description = "Optional document filename", example = "invoice.pdf")
    private String filename;
}
