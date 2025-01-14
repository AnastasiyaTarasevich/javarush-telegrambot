package com.github.javarushcommunity.jrtb.service;

import java.util.List;

public interface SendBotMessageService {

    void sendMessage(String chatId, String text);

    void sendMessage(String chatId, List<String> message);
}
