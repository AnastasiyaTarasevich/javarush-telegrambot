package com.github.javarushcommunity.jrtb.javarushclient.dto;

import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.Language;
import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.PostType;
import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.VisibilityStatus;
import lombok.Data;

@Data
public class PostInfo {
    private BaseUserInfo authorInfo;
    private Integer commentsCount;
    private Long createdTime;
    private String description;
    private GroupInfo groupInfo;
    private Integer id;
    private String key;
    private Language language;
    private LikesInfo likesInfo;
    private GroupInfo originalGroupInfo;
    private String pictureUrl;
    private Double rating;
    private Integer ratingCount;
    private String title;
    private PostType postType;
    private Long updatedTime;
    private UserDiscussionInfo userDiscussionInfo;
    private Integer views;
    private VisibilityStatus visibilityStatus;
}
