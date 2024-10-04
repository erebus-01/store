package io.store.user.utils.mapper;

import io.store.user.dto.request.GroupRequest;
import io.store.user.dto.response.GroupResponse;
import io.store.user.model.Group;
import io.store.user.model.Role;
import io.store.user.repository.RoleRepository;
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
public abstract class GroupMapper {

    @Autowired
    protected RoleRepository roleRepository;

    public static final GroupMapper instance = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    public abstract Group toEntity(GroupRequest groupRequest);

    public abstract GroupResponse toResponse(Group group);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    public abstract void updateEntity(GroupRequest groupRequest, @MappingTarget Group group);

    @Named("mapRoles")
    protected Set<Role> mapRoles(Set<Long> roles) {
        if (roles == null) {
            return new HashSet<>();
        }
        return roles.stream()
                .map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + id)))
                .collect(Collectors.toSet());
    }

}
