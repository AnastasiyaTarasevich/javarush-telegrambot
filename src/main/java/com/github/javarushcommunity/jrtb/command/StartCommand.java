package com.github.javarushcommunity.jrtb.command;


import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Привет. Я Javarush Telegram Bot.\n " +
            "Я помогу тебе быть в курсе последних статей тех авторов, которые тебе интересны.\n\n" +
            "Нажимай /addgroupsub чтобы подписаться на группу статей в JavaRush.\n" +
            "Не знаешь о чем я? Напиши /help, чтобы узнать что я умею.";


    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;

    }

    @Override
    public void execute(Update update) {

        String chat_id=update.getMessage().getChatId().toString();
        telegramUserService.findByChatId(chat_id).ifPresentOrElse(
                user->
                {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                ()->{
                    TelegramUser telegramUser =new TelegramUser();
                    telegramUser.setChatId(chat_id);
                    telegramUser.setActive(true);
                    telegramUserService.save(telegramUser);
                }
        );
        sendBotMessageService.sendMessage(chat_id, START_MESSAGE);

    }
}
