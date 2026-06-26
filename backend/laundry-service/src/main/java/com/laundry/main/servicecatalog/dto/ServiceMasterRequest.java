package com.laundry.main.servicecatalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(description = "ServiceMaster create or update request")
public class ServiceMasterRequest {

  @NotBlank(message = "Service name is required")
  @Schema(example = "Wash")
  private String serviceName;

  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
  @Schema(example = "25.00")
  private BigDecimal price;

  @Schema(example = "true")
  private Boolean active = true;
}
