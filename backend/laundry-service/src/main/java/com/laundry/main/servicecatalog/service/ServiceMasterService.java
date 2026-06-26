package com.laundry.main.servicecatalog.service;

import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import java.util.List;

public interface ServiceMasterService {

  ServiceMasterResponse createService(ServiceMasterRequest request);

  ServiceMasterResponse getServiceById(Long serviceId);

  List<ServiceMasterResponse> getAllServices();

  ServiceMasterResponse updateService(Long serviceId, ServiceMasterRequest request);

  void deleteService(Long serviceId);
}
