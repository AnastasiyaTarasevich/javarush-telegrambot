package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.bot.JavaRushTelegramBot;

import com.github.javarushcommunity.jrtb.service.Impl.SendBotMessageServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private JavaRushTelegramBot javaRushTelegramBot;

    @BeforeEach
    public void init() {
        javaRushTelegramBot= Mockito.mock(JavaRushTelegramBot.class);
        sendBotMessageService=new SendBotMessageServiceImpl(javaRushTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        String chatId = "test_chat_id";
        String message="test_message";

        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        sendBotMessageService.sendMessage(chatId, message);
        Mockito.verify(javaRushTelegramBot).execute(sendMessage);
    }
}
