package com.itlin.communityapi.controller;

import com.itlin.communityapi.service.LoginService;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    LoginService loginService;

    /**
     * 注册
     * @param loginParam
     * @return
     */
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);


    }

}
