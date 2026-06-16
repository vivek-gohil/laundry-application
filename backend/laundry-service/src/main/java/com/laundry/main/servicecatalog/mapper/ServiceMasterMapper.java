package com.laundry.main.servicecatalog.mapper;

import com.laundry.main.servicecatalog.dto.ServiceMasterRequest;
import com.laundry.main.servicecatalog.dto.ServiceMasterResponse;
import com.laundry.main.servicecatalog.entity.ServiceMaster;
import com.laundry.main.common.mapper.CentralMapperConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = CentralMapperConfig.class)
public interface ServiceMasterMapper {

    @Mapping(target = "serviceId", ignore = true)
    ServiceMaster toEntity(ServiceMasterRequest request);

    ServiceMasterResponse toResponse(ServiceMaster serviceMaster);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "serviceId", ignore = true)
    void updateEntity(ServiceMasterRequest request, @MappingTarget ServiceMaster serviceMaster);
}
