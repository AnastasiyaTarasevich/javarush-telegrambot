package com.github.javarushcommunity.jrtb.service.Impl;

import com.github.javarushcommunity.jrtb.bot.JavaRushTelegramBot;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot telegramBot;

    public SendBotMessageServiceImpl(JavaRushTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        try{
            telegramBot.execute(sendMessage);

        }catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}
