package io.store.user.utils.mapper;

import io.store.user.dto.request.AddressRequest;
import io.store.user.dto.response.AddressResponse;
import io.store.user.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper instance = Mappers.getMapper(AddressMapper.class);

    Address toEntity(AddressRequest addressRequest);

    AddressResponse toResponse(Address address);
}