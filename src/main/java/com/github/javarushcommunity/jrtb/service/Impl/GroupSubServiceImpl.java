package com.github.javarushcommunity.jrtb.service.Impl;

import com.github.javarushcommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.repository.GroupSubRepository;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class GroupSubServiceImpl implements GroupSubService {

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;
    private final JavaRushGroupClient javaRushGroupClient;

    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService, JavaRushGroupClient javaRushGroupClient) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
        this.javaRushGroupClient = javaRushGroupClient;
    }

    @Override
    public GroupSub save(Long chatId, GroupDiscussionInfo discussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB=groupSubRepository.findById(discussionInfo.getId());
        if (groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first=groupSub.getUsers().stream().filter(it->it.getChatId()
                    .equals(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        }else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setLastPostId(javaRushGroupClient.findLastPostId(discussionInfo.getId()));
            groupSub.setId(discussionInfo.getId());
            groupSub.setTitle(discussionInfo.getTitle());

        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(String chatId) {
        return groupSubRepository.findById(Integer.valueOf(chatId));
    }

    @Override
    public GroupSub save(GroupSub groupsub) {
        return groupSubRepository.save(groupsub);
    }

    @Override
    public List<GroupSub> findAll() {
        return groupSubRepository.findAll();
    }
}
