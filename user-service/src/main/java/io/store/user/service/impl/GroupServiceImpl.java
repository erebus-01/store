package io.store.user.service.impl;

import io.store.user.dto.request.GroupRequest;
import io.store.user.dto.response.GroupResponse;
import io.store.user.model.Group;
import io.store.user.repository.GroupRepository;
import io.store.user.service.GroupService;
import io.store.user.utils.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {
        Group group = groupMapper.toEntity(groupRequest);
        group = groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    @Override
    public GroupResponse updateGroup(Long id, GroupRequest groupRequest) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        groupMapper.updateEntity(groupRequest, group);
        group = groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    @Override
    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return groupMapper.toResponse(group);
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
