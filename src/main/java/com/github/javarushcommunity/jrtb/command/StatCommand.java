package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command{
    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;
    public final static String STAT_MESSAGE="JavaRush Telegram Bot использует %s человек.";

    public StatCommand( SendBotMessageService sendBotMessageService,TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount=telegramUserService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE,activeUserCount));
    }
}
