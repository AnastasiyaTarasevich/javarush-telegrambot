package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.dto.StatisticsDTO;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.StatisticsService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command{
   private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;
    public final static String STAT_MESSAGE="✨<b>Подготовил статистику</b>✨\n" +
            "- Количество активных пользователей: %s\n" +
            "- Количество неактивных пользователей: %s\n" +
            "- Среднее количество групп на одного пользователя: %s\n\n" +
            "<b>Информация по активным группам</b>:\n" +
            "%s";

    public StatCommand( SendBotMessageService sendBotMessageService,StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        StatisticsDTO statisticsDTO=statisticsService.countBotStatistics();
        String collectedGroups=statisticsDTO.getGroupStatDTOs().stream()
                .map(it->String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), String.format(STAT_MESSAGE,
                statisticsDTO.getActiveUserCount(),
                statisticsDTO.getInactiveUserCount(),
                statisticsDTO.getAverageGroupCountByUser(),
                collectedGroups));


    }
}
