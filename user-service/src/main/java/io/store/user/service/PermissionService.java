package io.store.user.service;

import io.store.user.dto.request.PermissionRequest;
import io.store.user.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest permissionRequest);
    PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest);
    PermissionResponse getPermissionById(Long id);
    List<PermissionResponse> getAllPermissions();
    void deletePermission(Long id);
}
