package com.laundry.main.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Error response")
public class ErrorResponse {

  @Schema(example = "2026-06-15T20:45:00")
  private LocalDateTime timestamp;

  @Schema(example = "404")
  private int status;

  @Schema(example = "Not Found")
  private String error;

  @Schema(example = "Customer not found with id: 1")
  private String message;

  @Schema(example = "/api/customers/1")
  private String path;

  private Map<String, String> validationErrors;
}
