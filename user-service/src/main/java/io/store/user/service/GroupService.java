package io.store.user.service;

import io.store.user.dto.request.GroupRequest;
import io.store.user.dto.response.GroupResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRequest groupRequest);
    GroupResponse updateGroup(Long id, GroupRequest groupRequest);
    GroupResponse getGroupById(Long id);
    List<GroupResponse> getAllGroups();
    void deleteGroup(Long id);
}
