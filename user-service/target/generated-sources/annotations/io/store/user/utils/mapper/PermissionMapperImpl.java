package io.store.user.utils.mapper;

import io.store.user.dto.request.PermissionRequest;
import io.store.user.dto.response.PermissionResponse;
import io.store.user.model.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T01:26:31+0700",
    comments = "version: 1.6.0.Beta2, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toEntity(PermissionRequest permissionRequest) {
        if ( permissionRequest == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setName( permissionRequest.getName() );

        return permission;
    }

    @Override
    public PermissionResponse toResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setId( permission.getId() );
        permissionResponse.setName( permission.getName() );

        return permissionResponse;
    }

    @Override
    public void updateEntity(PermissionRequest permissionRequest, Permission permission) {
        if ( permissionRequest == null ) {
            return;
        }

        permission.setName( permissionRequest.getName() );
    }
}
