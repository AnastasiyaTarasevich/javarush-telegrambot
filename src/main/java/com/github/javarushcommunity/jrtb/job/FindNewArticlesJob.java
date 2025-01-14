package com.github.javarushcommunity.jrtb.job;

import com.github.javarushcommunity.jrtb.service.FindNewArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class FindNewArticlesJob {
    private final FindNewArticleService findNewArticleService;

    public FindNewArticlesJob(FindNewArticleService findNewArticleService) {
        this.findNewArticleService = findNewArticleService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewArticleFixedRate}")
    public void findNewArticles() {
        LocalDateTime start = LocalDateTime.now();
        log.info("FindNewArticles start");
        findNewArticleService.findNewArticles();
        LocalDateTime end = LocalDateTime.now();
        log.info("FindNewArticles end. Took seconds:{}",end.toEpochSecond(ZoneOffset.UTC)
                -start.toEpochSecond(ZoneOffset.UTC));
    }
}
