package com.github.javarushcommunity.jrtb.service;

import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);

    List<TelegramUser> findAllActiveUsers();

    Optional<TelegramUser> findByChatId(Long chatId);

    List<TelegramUser> findAllInActiveUsers();


}
