package io.store.user.utils.mapper;

import io.store.user.dto.request.RoleRequest;
import io.store.user.dto.response.RoleResponse;
import io.store.user.model.Permission;
import io.store.user.model.Role;
import io.store.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    @Autowired
    protected PermissionRepository permissionRepository;

    public static final RoleMapper instance = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissions")
    public abstract Role toEntity(RoleRequest roleRequest);

    public abstract RoleResponse toResponse(Role role);

    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissions")
    public abstract void updateEntity(RoleRequest roleRequest, @MappingTarget Role role);

    @Named("mapPermissions")
    protected Set<Permission> mapPermissions(Set<Long> permissions) {
        if (permissions == null) {
            return new HashSet<>();
        }
        return permissions.stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}
