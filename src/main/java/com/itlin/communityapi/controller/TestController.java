package com.itlin.communityapi.controller;

import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        User user = UserThreadLocal.get();
        System.out.println(user);
        return Result.success(null);
    }
}

// nohup java -jar community-api-0.0.1-SNAPSHOT.jar  >  community.log &