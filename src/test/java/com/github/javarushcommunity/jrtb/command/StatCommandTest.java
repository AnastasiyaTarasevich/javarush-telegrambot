package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.dto.GroupStatDTO;
import com.github.javarushcommunity.jrtb.dto.StatisticsDTO;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.javarushcommunity.jrtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.javarushcommunity.jrtb.command.StatCommand.STAT_MESSAGE;
import static java.lang.String.format;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {


    private StatisticsService statisticsService;
    private Command command;
    private SendBotMessageService sendBotMessageService;

    @BeforeEach
    public void init() {
        sendBotMessageService= Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        command = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage() {
        Long chatId = 1234567L;
        GroupStatDTO groupStatDTO=new GroupStatDTO(1,"group",1);
        StatisticsDTO statisticsDTO=new StatisticsDTO(1,1, Collections.singletonList(groupStatDTO),2.5);
        Mockito.when(statisticsService.countBotStatistics())
                .thenReturn(statisticsDTO);

        command.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        Mockito.verify(sendBotMessageService).sendMessage(chatId,format(STAT_MESSAGE,
                statisticsDTO.getActiveUserCount(),
                statisticsDTO.getInactiveUserCount(),
                statisticsDTO.getAverageGroupCountByUser(),
                format("%s (id = %s) - %s подписчиков", groupStatDTO.getTitle(), groupStatDTO.getId(), groupStatDTO.getActiveUserCount())
        ));
    }
}
