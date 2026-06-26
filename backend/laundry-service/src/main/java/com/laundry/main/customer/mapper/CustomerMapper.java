package com.laundry.main.customer.mapper;

import com.laundry.main.common.mapper.CentralMapperConfig;
import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import com.laundry.main.customer.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = CentralMapperConfig.class)
public interface CustomerMapper {

  @Mapping(target = "customerId", ignore = true)
  Customer toEntity(CustomerRequest request);

  CustomerResponse toResponse(Customer customer);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CustomerRequest request, @MappingTarget Customer customer);
}
