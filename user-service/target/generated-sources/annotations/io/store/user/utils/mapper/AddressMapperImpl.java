package io.store.user.utils.mapper;

import io.store.user.dto.request.AddressRequest;
import io.store.user.dto.response.AddressResponse;
import io.store.user.model.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T01:26:31+0700",
    comments = "version: 1.6.0.Beta2, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toEntity(AddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.street( addressRequest.getStreet() );
        address.city( addressRequest.getCity() );
        address.state( addressRequest.getState() );
        address.postalCode( addressRequest.getPostalCode() );
        address.country( addressRequest.getCountry() );

        return address.build();
    }

    @Override
    public AddressResponse toResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse.AddressResponseBuilder addressResponse = AddressResponse.builder();

        addressResponse.id( address.getId() );
        addressResponse.street( address.getStreet() );
        addressResponse.city( address.getCity() );
        addressResponse.state( address.getState() );
        addressResponse.country( address.getCountry() );
        addressResponse.postalCode( address.getPostalCode() );

        return addressResponse.build();
    }
}
