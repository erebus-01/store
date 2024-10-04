package io.store.user.utils.mapper;

import io.store.user.dto.request.UserRequest;
import io.store.user.dto.response.UserResponse;
import io.store.user.model.Group;
import io.store.user.model.Role;
import io.store.user.model.User;
import io.store.user.repository.GroupRepository;
import io.store.user.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, RoleMapper.class, GroupMapper.class})
public abstract class UserMapper {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected GroupRepository groupRepository;

    public static final UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "userMapRoles")
    @Mapping(target = "groups", source = "groupIds", qualifiedByName = "userMapGroups")
    public abstract User toEntity(UserRequest userRequest);

    public abstract UserResponse toResponse(User user);

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "userMapRoles")
    @Mapping(target = "groups", source = "groupIds", qualifiedByName = "userMapGroups")
    public abstract void updateEntity(UserRequest userRequest, @MappingTarget User user);

    @Named("userMapRoles")
    protected Set<Role> mapRoles(Set<Long> roleIds) {
        if (roleIds == null) {
            return new HashSet<>();
        }
        return roleIds.stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + id)))
                .collect(Collectors.toSet());
    }

    @Named("userMapGroups")
    protected Set<Group> mapGroups(Set<Long> groupIds) {
        if (groupIds == null) {
            return new HashSet<>();
        }
        return groupIds.stream()
                .map(id -> groupRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Group not found with id: " + id)))
                .collect(Collectors.toSet());
    }
}

