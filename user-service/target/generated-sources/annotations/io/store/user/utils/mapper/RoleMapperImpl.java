package io.store.user.utils.mapper;

import io.store.user.dto.request.RoleRequest;
import io.store.user.dto.response.PermissionResponse;
import io.store.user.dto.response.RoleResponse;
import io.store.user.model.Permission;
import io.store.user.model.Role;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T01:26:31+0700",
    comments = "version: 1.6.0.Beta2, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl extends RoleMapper {

    @Override
    public Role toEntity(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.permissions( mapPermissions( roleRequest.getPermissions() ) );
        role.name( roleRequest.getName() );

        return role.build();
    }

    @Override
    public RoleResponse toResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setId( role.getId() );
        roleResponse.setName( role.getName() );
        roleResponse.setPermissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse;
    }

    @Override
    public void updateEntity(RoleRequest roleRequest, Role role) {
        if ( roleRequest == null ) {
            return;
        }

        if ( role.getPermissions() != null ) {
            Set<Permission> set = mapPermissions( roleRequest.getPermissions() );
            if ( set != null ) {
                role.getPermissions().clear();
                role.getPermissions().addAll( set );
            }
            else {
                role.setPermissions( null );
            }
        }
        else {
            Set<Permission> set = mapPermissions( roleRequest.getPermissions() );
            if ( set != null ) {
                role.setPermissions( set );
            }
        }
        role.setName( roleRequest.getName() );
    }

    protected PermissionResponse permissionToPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setId( permission.getId() );
        permissionResponse.setName( permission.getName() );

        return permissionResponse;
    }

    protected Set<PermissionResponse> permissionSetToPermissionResponseSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionResponse> set1 = new LinkedHashSet<PermissionResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionResponse( permission ) );
        }

        return set1;
    }
}
