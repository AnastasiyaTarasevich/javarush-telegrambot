package com.github.javarushcommunity.jrtb.command;


import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.Impl.SendBotMessageServiceImpl;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.javarushcommunity.jrtb.command.CommandName.LIST_GROUP_SUB;

@DisplayName("Unit-level testing for ListGroupSubCommand")
public class ListGroupSubCommandTest {


    @Test
    public void shouldProperlyShowsListGroupSub()
    {
        TelegramUser user = new TelegramUser();
        user.setActive(true);
        user.setChatId("1");

        List<GroupSub> groups = new ArrayList<>();
        groups.add(populateGroupSub(1,"g1"));
        groups.add(populateGroupSub(2,"g2"));
        groups.add(populateGroupSub(3,"g3"));
        groups.add(populateGroupSub(4,"g4"));


        user.setGroupSubs(groups);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(user.getChatId())).thenReturn(Optional.of(user));

        ListGroupSubCommand command=new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(user.getChatId()));
        Mockito.when(message.getText()).thenReturn(LIST_GROUP_SUB.getCommandName());
        update.setMessage(message);

        String collectedGroups="Я нашел все подписки на группы: \n\n"+
                user.getGroupSubs().stream()
                        .map(it->"Группа: "+it.getTitle()+" , ID= "+it.getId()+" \n")
                        .collect(Collectors.joining());
        command.execute(update);
        Mockito.verify(sendBotMessageService).sendMessage(user.getChatId(), collectedGroups);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);
        return gs;
    }
}
