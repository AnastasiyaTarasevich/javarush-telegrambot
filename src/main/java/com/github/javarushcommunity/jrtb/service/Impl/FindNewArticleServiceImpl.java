package com.github.javarushcommunity.jrtb.service.Impl;

import com.github.javarushcommunity.jrtb.javarushclient.JavaRushPostClient;
import com.github.javarushcommunity.jrtb.javarushclient.dto.PostInfo;
import com.github.javarushcommunity.jrtb.repository.entity.GroupSub;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.FindNewArticleService;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.SendBotMessageService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class FindNewArticleServiceImpl implements FindNewArticleService {


    public static final String JAVARUSH_WEB_POST_FORMAT="https://javarush.com/groups/posts/%s";
    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    public FindNewArticleServiceImpl(GroupSubService groupSubService, JavaRushPostClient javaRushPostClient, SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(grSub->
        {
            List<PostInfo> newPosts=javaRushPostClient.findNewPosts(grSub.getId(),grSub.getLastArticleId());
            setNewArticleId(grSub, newPosts);
            notifySubscribersAboutNewArticles(grSub, newPosts);
        });
    }

    private void setNewArticleId(GroupSub grSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id->
                {
                    grSub.setLastArticleId(id);
                    groupSubService.save(grSub);
                });
    }
    private void notifySubscribersAboutNewArticles(GroupSub grSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья: <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                                "<b>Описание:</b> %s\n\n" +
                                "<b>Ссылка:</b> %s\n",
                        post.getTitle(), grSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());
        grSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it->sendBotMessageService.sendMessage(it.getChatId(), messagesWithNewArticles));
    }
    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
