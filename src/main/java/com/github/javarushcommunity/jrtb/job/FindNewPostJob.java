package com.github.javarushcommunity.jrtb.job;

import com.github.javarushcommunity.jrtb.service.FindNewPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class FindNewPostJob {
    private final FindNewPostService findNewPostService;

    public FindNewPostJob(FindNewPostService findNewPostService) {
        this.findNewPostService = findNewPostService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewPostFixedRate}")
    public void findNewPosts() {
        LocalDateTime start = LocalDateTime.now();
        log.info("FindNewArticles start");
        findNewPostService.findNewPosts();
        LocalDateTime end = LocalDateTime.now();
        log.info("FindNewArticles end. Took seconds:{}",end.toEpochSecond(ZoneOffset.UTC)
                -start.toEpochSecond(ZoneOffset.UTC));
    }
}
