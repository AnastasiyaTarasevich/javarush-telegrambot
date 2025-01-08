package com.github.javarushcommunity.jrtb.command;

import com.github.javarushcommunity.jrtb.bot.JavaRushTelegramBot;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import com.github.javarushcommunity.jrtb.service.Impl.SendBotMessageServiceImpl;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractCommandTest {

    protected JavaRushTelegramBot bot= Mockito.mock(JavaRushTelegramBot.class);
    protected SendBotMessageService sendBotMessageService= new SendBotMessageServiceImpl(bot);
    protected TelegramUserService telegramUserService=Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();


    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        Long chatId=1234567824356L;

        Update update = new Update();

        Message message = Mockito.mock(Message.class);

        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        getCommand().execute(update);

        Mockito.verify(bot).execute(sendMessage);
    }

}
