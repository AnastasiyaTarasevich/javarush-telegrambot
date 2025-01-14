package com.github.javarushcommunity.jrtb.javarushclient;

import com.github.javarushcommunity.jrtb.javarushclient.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaRushPostClientImpl implements JavaRushPostClient {

    private final String javarushApiPostPath;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}")String javarushApi) {
        this.javarushApiPostPath = javarushApi+"/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
       List<PostInfo> lastPostByGroup = Unirest.get(javarushApiPostPath)
               .queryString("order","NEW")
               .queryString("groupKid",groupId)
               .queryString("limit",15)
               .asObject(new GenericType<List<PostInfo>>() {})
               .getBody();
       List<PostInfo> newPostByGroup = new ArrayList<>();
       for (PostInfo post : lastPostByGroup) {
           if(lastPostId.equals(post.getId())){
               return newPostByGroup;
           }
           newPostByGroup.add(post);
       }

        return newPostByGroup;
    }
}
