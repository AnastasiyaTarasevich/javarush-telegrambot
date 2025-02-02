package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

public interface GroupSubService {
    GroupSub save(Long chatId, GroupDiscussionInfo discussionInfo);
    Optional<GroupSub> findById(String chatId);
    GroupSub save(GroupSub groupsub);
    List<GroupSub> findAll();
}
