package io.store.user.utils.mapper;

import io.store.user.dto.request.PermissionRequest;
import io.store.user.dto.response.PermissionResponse;
import io.store.user.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionMapper instance = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionRequest permissionRequest);
    PermissionResponse toResponse(Permission permission);
    void updateEntity(PermissionRequest permissionRequest, @MappingTarget Permission permission);
}
