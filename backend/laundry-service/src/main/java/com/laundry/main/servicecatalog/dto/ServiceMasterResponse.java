package com.laundry.main.servicecatalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "ServiceMaster response")
public class ServiceMasterResponse {

  @Schema(example = "1")
  private Long serviceId;

  @Schema(example = "Wash")
  private String serviceName;

  @Schema(example = "25.00")
  private BigDecimal price;

  @Schema(example = "true")
  private Boolean active;
}
