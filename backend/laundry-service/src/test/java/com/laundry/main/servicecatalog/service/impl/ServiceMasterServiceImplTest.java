package com.laundry.main.servicecatalog.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laundry.main.exception.DuplicateResourceException;
import com.laundry.main.exception.ResourceNotFoundException;
import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import com.laundry.main.servicecatalog.entity.ServiceMaster;
import com.laundry.main.servicecatalog.mapper.ServiceMasterMapper;
import com.laundry.main.servicecatalog.repository.ServiceMasterRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceMasterServiceImplTest {

  @Mock private ServiceMasterRepository serviceMasterRepository;

  @Mock private ServiceMasterMapper serviceMasterMapper;

  @InjectMocks private ServiceMasterServiceImpl serviceMasterService;

  @Test
  void createServiceSavesAndReturnsService() {
    ServiceMasterRequest request = serviceMasterRequest();
    ServiceMaster serviceMaster = serviceMaster();
    ServiceMaster savedServiceMaster = serviceMaster();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterRepository.existsByServiceName(request.getServiceName())).thenReturn(false);
    when(serviceMasterMapper.toEntity(request)).thenReturn(serviceMaster);
    when(serviceMasterRepository.save(serviceMaster)).thenReturn(savedServiceMaster);
    when(serviceMasterMapper.toResponse(savedServiceMaster)).thenReturn(response);

    ServiceMasterResponse result = serviceMasterService.createService(request);

    assertThat(result).isEqualTo(response);
    verify(serviceMasterRepository).save(serviceMaster);
  }

  @Test
  void createServiceThrowsExceptionWhenServiceNameAlreadyExists() {
    ServiceMasterRequest request = serviceMasterRequest();

    when(serviceMasterRepository.existsByServiceName(request.getServiceName())).thenReturn(true);

    assertThatThrownBy(() -> serviceMasterService.createService(request))
        .isInstanceOf(DuplicateResourceException.class)
        .hasMessage("Service already exists with name: Wash");
  }

  @Test
  void getServiceByIdReturnsService() {
    ServiceMaster serviceMaster = serviceMaster();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterRepository.findById(1L)).thenReturn(Optional.of(serviceMaster));
    when(serviceMasterMapper.toResponse(serviceMaster)).thenReturn(response);

    ServiceMasterResponse result = serviceMasterService.getServiceById(1L);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void getServiceByIdThrowsExceptionWhenServiceNotFound() {
    when(serviceMasterRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> serviceMasterService.getServiceById(99L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("Service not found with id: 99");
  }

  @Test
  void getAllServicesReturnsServices() {
    ServiceMaster serviceMaster = serviceMaster();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterRepository.findAll()).thenReturn(List.of(serviceMaster));
    when(serviceMasterMapper.toResponse(serviceMaster)).thenReturn(response);

    List<ServiceMasterResponse> result = serviceMasterService.getAllServices();

    assertThat(result).containsExactly(response);
  }

  @Test
  void updateServiceUpdatesAndReturnsService() {
    ServiceMasterRequest request = serviceMasterRequest();
    ServiceMaster serviceMaster = serviceMaster();
    ServiceMaster savedServiceMaster = serviceMaster();
    ServiceMasterResponse response = serviceMasterResponse();

    when(serviceMasterRepository.findById(1L)).thenReturn(Optional.of(serviceMaster));
    when(serviceMasterRepository.existsByServiceNameAndServiceIdNot(request.getServiceName(), 1L))
        .thenReturn(false);
    when(serviceMasterRepository.save(serviceMaster)).thenReturn(savedServiceMaster);
    when(serviceMasterMapper.toResponse(savedServiceMaster)).thenReturn(response);

    ServiceMasterResponse result = serviceMasterService.updateService(1L, request);

    assertThat(result).isEqualTo(response);
    verify(serviceMasterMapper).updateEntity(request, serviceMaster);
  }

  @Test
  void deleteServiceDeletesExistingService() {
    ServiceMaster serviceMaster = serviceMaster();

    when(serviceMasterRepository.findById(1L)).thenReturn(Optional.of(serviceMaster));
    doNothing().when(serviceMasterRepository).delete(serviceMaster);

    serviceMasterService.deleteService(1L);

    verify(serviceMasterRepository).delete(serviceMaster);
  }

  private ServiceMasterRequest serviceMasterRequest() {
    ServiceMasterRequest request = new ServiceMasterRequest();
    request.setServiceName("Wash");
    request.setPrice(new BigDecimal("25.00"));
    request.setActive(true);
    return request;
  }

  private ServiceMaster serviceMaster() {
    return ServiceMaster.builder()
        .serviceId(1L)
        .serviceName("Wash")
        .price(new BigDecimal("25.00"))
        .active(true)
        .build();
  }

  private ServiceMasterResponse serviceMasterResponse() {
    return ServiceMasterResponse.builder()
        .serviceId(1L)
        .serviceName("Wash")
        .price(new BigDecimal("25.00"))
        .active(true)
        .build();
  }
}
