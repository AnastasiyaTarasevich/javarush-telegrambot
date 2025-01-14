package com.github.javarushcommunity.jrtb.javarushclient.dto;

import com.github.javarushcommunity.jrtb.javarushclient.dto.enums.MeGroupInfoStatus;
import lombok.Data;

@Data
public class MeGroupInfo {
    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
