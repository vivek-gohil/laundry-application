package com.laundry.main.customer.mapper;

import com.laundry.main.customer.Customer;
import com.laundry.main.customer.dto.CustomerRequest;
import com.laundry.main.customer.dto.CustomerResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "customerId", ignore = true)
    Customer toEntity(CustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(CustomerRequest request, @MappingTarget Customer customer);
}
