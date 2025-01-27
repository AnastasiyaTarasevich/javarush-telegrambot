package com.github.javarushcommunity.jrtb.service.Impl;

import com.github.javarushcommunity.jrtb.dto.GroupStatDTO;
import com.github.javarushcommunity.jrtb.dto.StatisticsDTO;
import com.github.javarushcommunity.jrtb.repository.entity.TelegramUser;
import com.github.javarushcommunity.jrtb.service.GroupSubService;
import com.github.javarushcommunity.jrtb.service.StatisticsService;
import com.github.javarushcommunity.jrtb.service.TelegramUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final GroupSubService groupSubService;
    private final TelegramUserService telegramUserService;

    public StatisticsServiceImpl(GroupSubService groupSubService, TelegramUserService telegramUserService) {
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StatisticsDTO countBotStatistics() {
        List<GroupStatDTO> groupStatDTOS=groupSubService.findAll().stream()
                .filter(it->!isEmpty(it.getUsers()))
                .map(groupSub->new GroupStatDTO(groupSub.getId(),groupSub.getTitle(), groupSub.getUsers().size()))
                .collect(Collectors.toList());
        List<TelegramUser> allInActiveUsers=telegramUserService.findAllInActiveUsers();
        List<TelegramUser> allActiveUsers=telegramUserService.findAllActiveUsers();
        double groupPerUser=getGroupsPerUser(allActiveUsers);

        return new StatisticsDTO(allActiveUsers.size(),allInActiveUsers.size(),groupStatDTOS,groupPerUser);
    }

    private double getGroupsPerUser(List<TelegramUser> allActiveUsers) {
        return (double) allActiveUsers.stream().mapToInt(it->it.getGroupSubs().size()).sum()/allActiveUsers.size();
    }
}
