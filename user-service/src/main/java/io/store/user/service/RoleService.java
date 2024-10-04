package io.store.user.service;

import io.store.user.dto.request.RoleRequest;
import io.store.user.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse updateRole(Long id, RoleRequest roleRequest);
    RoleResponse getRoleById(Long id);
    List<RoleResponse> getAllRoles();
    void deleteRole(Long id);
}
