package com.github.javarushcommunity.jrtb.service.Impl;

import com.github.javarushcommunity.jrtb.bot.JavaRushTelegramBot;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot telegramBot;

    public SendBotMessageServiceImpl(JavaRushTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        try{
            telegramBot.execute(sendMessage);

        }catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void sendMessage(Long chatId, List<String> messages) {
        if (isEmpty(messages)) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }
}
