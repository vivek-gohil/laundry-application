package com.laundry.main.servicecatalog.service.impl;

import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import com.laundry.main.servicecatalog.entity.ServiceMaster;
import com.laundry.main.servicecatalog.mapper.ServiceMasterMapper;
import com.laundry.main.servicecatalog.repository.ServiceMasterRepository;
import com.laundry.main.servicecatalog.service.ServiceMasterService;
import com.laundry.main.exception.DuplicateResourceException;
import com.laundry.main.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceMasterServiceImpl implements ServiceMasterService {

    private final ServiceMasterRepository serviceMasterRepository;
    private final ServiceMasterMapper serviceMasterMapper;

    @Override
    public ServiceMasterResponse createService(ServiceMasterRequest request) {
        validateUniqueService(request, null);
        ServiceMaster serviceMaster = serviceMasterMapper.toEntity(request);
        if (serviceMaster.getActive() == null) {
            serviceMaster.setActive(true);
        }
        return serviceMasterMapper.toResponse(serviceMasterRepository.save(serviceMaster));
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceMasterResponse getServiceById(Long serviceId) {
        return serviceMasterMapper.toResponse(findService(serviceId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceMasterResponse> getAllServices() {
        return serviceMasterRepository.findAll()
                .stream()
                .map(serviceMasterMapper::toResponse)
                .toList();
    }

    @Override
    public ServiceMasterResponse updateService(Long serviceId, ServiceMasterRequest request) {
        ServiceMaster serviceMaster = findService(serviceId);
        validateUniqueService(request, serviceId);
        serviceMasterMapper.updateEntity(request, serviceMaster);
        if (serviceMaster.getActive() == null) {
            serviceMaster.setActive(true);
        }
        return serviceMasterMapper.toResponse(serviceMasterRepository.save(serviceMaster));
    }

    @Override
    public void deleteService(Long serviceId) {
        ServiceMaster serviceMaster = findService(serviceId);
        serviceMasterRepository.delete(serviceMaster);
    }

    private ServiceMaster findService(Long serviceId) {
        return serviceMasterRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
    }

    private void validateUniqueService(ServiceMasterRequest request, Long serviceId) {
        boolean exists = serviceId == null
                ? serviceMasterRepository.existsByServiceName(request.getServiceName())
                : serviceMasterRepository.existsByServiceNameAndServiceIdNot(request.getServiceName(), serviceId);
        if (exists) {
            throw new DuplicateResourceException("Service already exists with name: " + request.getServiceName());
        }
    }
}
