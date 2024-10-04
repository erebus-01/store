package io.store.user.service.impl;

import io.store.user.dto.request.PermissionRequest;
import io.store.user.dto.response.PermissionResponse;
import io.store.user.model.Permission;
import io.store.user.repository.PermissionRepository;
import io.store.user.service.PermissionService;
import io.store.user.utils.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = PermissionMapper.instance.toEntity(permissionRequest);
        permission = permissionRepository.save(permission);
        return PermissionMapper.instance.toResponse(permission);
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        PermissionMapper.instance.updateEntity(permissionRequest, permission);
        permission = permissionRepository.save(permission);
        return PermissionMapper.instance.toResponse(permission);
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return PermissionMapper.instance.toResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper.instance::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

}
