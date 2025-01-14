package com.github.javarushcommunity.jrtb.javarushclient.dto;

import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.GroupInfoType;
import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.GroupVisibilityStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupInfo {
    private Integer id;
    private String avatarUrl;
    private String createTime;
    private String description;
    private String key;
    private Integer levelToEditor;
    private MeGroupInfo meGroupInfo;
    private String pictureUrl;
    private String title;
    private GroupInfoType type;
    private Integer userCount;
    private GroupVisibilityStatus visibilityStatus;
}
