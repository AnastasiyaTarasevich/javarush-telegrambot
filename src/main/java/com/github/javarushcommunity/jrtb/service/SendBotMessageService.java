package com.github.javarushcommunity.jrtb.service;

import java.util.List;

public interface SendBotMessageService {

    void sendMessage(Long chatId, String text);

    void sendMessage(Long chatId, List<String> message);
}
