package com.github.javarushcommunity.jrtb.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="tg_user")
public class TelegramUser {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    private boolean active;


}
