package io.store.user.utils.mapper;

import io.store.user.dto.request.GroupRequest;
import io.store.user.dto.response.GroupResponse;
import io.store.user.dto.response.PermissionResponse;
import io.store.user.dto.response.RoleResponse;
import io.store.user.model.Group;
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
public class GroupMapperImpl extends GroupMapper {

    @Override
    public Group toEntity(GroupRequest groupRequest) {
        if ( groupRequest == null ) {
            return null;
        }

        Group group = new Group();

        group.setRoles( mapRoles( groupRequest.getRoles() ) );
        group.setName( groupRequest.getName() );

        return group;
    }

    @Override
    public GroupResponse toResponse(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupResponse groupResponse = new GroupResponse();

        groupResponse.setId( group.getId() );
        groupResponse.setName( group.getName() );
        groupResponse.setRoles( roleSetToRoleResponseSet( group.getRoles() ) );

        return groupResponse;
    }

    @Override
    public void updateEntity(GroupRequest groupRequest, Group group) {
        if ( groupRequest == null ) {
            return;
        }

        if ( group.getRoles() != null ) {
            Set<Role> set = mapRoles( groupRequest.getRoles() );
            if ( set != null ) {
                group.getRoles().clear();
                group.getRoles().addAll( set );
            }
            else {
                group.setRoles( null );
            }
        }
        else {
            Set<Role> set = mapRoles( groupRequest.getRoles() );
            if ( set != null ) {
                group.setRoles( set );
            }
        }
        group.setName( groupRequest.getName() );
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

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setId( role.getId() );
        roleResponse.setName( role.getName() );
        roleResponse.setPermissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse;
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }
}
