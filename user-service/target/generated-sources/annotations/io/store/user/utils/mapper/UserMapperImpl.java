package io.store.user.utils.mapper;

import io.store.user.dto.request.AddressRequest;
import io.store.user.dto.request.UserRequest;
import io.store.user.dto.response.AddressResponse;
import io.store.user.dto.response.GroupResponse;
import io.store.user.dto.response.RoleResponse;
import io.store.user.dto.response.UserResponse;
import io.store.user.model.Address;
import io.store.user.model.Group;
import io.store.user.model.Role;
import io.store.user.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T11:21:02+0700",
    comments = "version: 1.6.0.Beta2, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.roles( mapRoles( userRequest.getRoleIds() ) );
        user.groups( mapGroups( userRequest.getGroupIds() ) );
        user.username( userRequest.getUsername() );
        user.password( userRequest.getPassword() );
        user.firstName( userRequest.getFirstName() );
        user.lastName( userRequest.getLastName() );
        user.email( userRequest.getEmail() );
        user.phoneNumber( userRequest.getPhoneNumber() );
        user.dateOfBirth( userRequest.getDateOfBirth() );
        user.gender( userRequest.getGender() );
        user.status( userRequest.getStatus() );
        user.addresses( addressRequestSetToAddressSet( userRequest.getAddresses() ) );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.email( user.getEmail() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.dateOfBirth( user.getDateOfBirth() );
        userResponse.gender( user.getGender() );
        userResponse.status( user.getStatus() );
        userResponse.roles( roleSetToRoleResponseSet( user.getRoles() ) );
        userResponse.groups( groupSetToGroupResponseSet( user.getGroups() ) );
        userResponse.addresses( addressSetToAddressResponseSet( user.getAddresses() ) );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        return userResponse.build();
    }

    @Override
    public void updateEntity(UserRequest userRequest, User user) {
        if ( userRequest == null ) {
            return;
        }

        if ( user.getRoles() != null ) {
            Set<Role> set = mapRoles( userRequest.getRoleIds() );
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
            else {
                user.setRoles( null );
            }
        }
        else {
            Set<Role> set = mapRoles( userRequest.getRoleIds() );
            if ( set != null ) {
                user.setRoles( set );
            }
        }
        if ( user.getGroups() != null ) {
            Set<Group> set1 = mapGroups( userRequest.getGroupIds() );
            if ( set1 != null ) {
                user.getGroups().clear();
                user.getGroups().addAll( set1 );
            }
            else {
                user.setGroups( null );
            }
        }
        else {
            Set<Group> set1 = mapGroups( userRequest.getGroupIds() );
            if ( set1 != null ) {
                user.setGroups( set1 );
            }
        }
        user.setUsername( userRequest.getUsername() );
        user.setPassword( userRequest.getPassword() );
        user.setFirstName( userRequest.getFirstName() );
        user.setLastName( userRequest.getLastName() );
        user.setEmail( userRequest.getEmail() );
        user.setPhoneNumber( userRequest.getPhoneNumber() );
        user.setDateOfBirth( userRequest.getDateOfBirth() );
        user.setGender( userRequest.getGender() );
        user.setStatus( userRequest.getStatus() );
        if ( user.getAddresses() != null ) {
            Set<Address> set2 = addressRequestSetToAddressSet( userRequest.getAddresses() );
            if ( set2 != null ) {
                user.getAddresses().clear();
                user.getAddresses().addAll( set2 );
            }
            else {
                user.setAddresses( null );
            }
        }
        else {
            Set<Address> set2 = addressRequestSetToAddressSet( userRequest.getAddresses() );
            if ( set2 != null ) {
                user.setAddresses( set2 );
            }
        }
    }

    protected Set<Address> addressRequestSetToAddressSet(Set<AddressRequest> set) {
        if ( set == null ) {
            return null;
        }

        Set<Address> set1 = new LinkedHashSet<Address>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AddressRequest addressRequest : set ) {
            set1.add( addressMapper.toEntity( addressRequest ) );
        }

        return set1;
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleMapper.toResponse( role ) );
        }

        return set1;
    }

    protected Set<GroupResponse> groupSetToGroupResponseSet(Set<Group> set) {
        if ( set == null ) {
            return null;
        }

        Set<GroupResponse> set1 = new LinkedHashSet<GroupResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Group group : set ) {
            set1.add( groupMapper.toResponse( group ) );
        }

        return set1;
    }

    protected Set<AddressResponse> addressSetToAddressResponseSet(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        Set<AddressResponse> set1 = new LinkedHashSet<AddressResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Address address : set ) {
            set1.add( addressMapper.toResponse( address ) );
        }

        return set1;
    }
}
