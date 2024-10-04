package io.store.user.service.impl;

import io.store.user.dto.request.RoleRequest;
import io.store.user.dto.response.RoleResponse;
import io.store.user.model.Role;
import io.store.user.repository.RoleRepository;
import io.store.user.service.RoleService;
import io.store.user.utils.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toEntity(roleRequest);
        log.info("role created: {}", role);
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleMapper.updateEntity(roleRequest, role);
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
