package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javarushcommunity.jrtb.repository.GroupSubRepository;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.Impl.GroupSubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for GroupSubService")
public class GroupSubServiceTest {
    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser newUser;
    private final static String CHAT_ID="1";

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService= Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService=new GroupSubServiceImpl(groupSubRepository, telegramUserService);
        newUser=new TelegramUser();
        newUser.setChatId(CHAT_ID);
        newUser.setActive(true);
        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveGroup() {

        GroupDiscussionInfo groupDiscussionInfo=new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub groupSub=new GroupSub();
        groupSub.setId(groupDiscussionInfo.getId());
        groupSub.setTitle(groupDiscussionInfo.getTitle());
        groupSub.addUser(newUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);
        Mockito.verify(groupSubRepository).save(groupSub);
    }

    @Test
    public void shouldProperlyAddUserToExistingGroup() {
        TelegramUser oldTelegramUser=new TelegramUser();
        oldTelegramUser.setChatId("2");
        oldTelegramUser.setActive(true);

        GroupDiscussionInfo groupDiscussionInfo=new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub groupSub=new GroupSub();
        groupSub.setId(groupDiscussionInfo.getId());
        groupSub.setTitle(groupDiscussionInfo.getTitle());
        groupSub.addUser(oldTelegramUser);

        Mockito.when(groupSubRepository.findById(groupDiscussionInfo.getId())).thenReturn(Optional.of(groupSub));

        GroupSub expectedGroupSub=new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(oldTelegramUser);
        expectedGroupSub.addUser(newUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        Mockito.verify(groupSubRepository).findById(groupDiscussionInfo.getId());
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }
}
