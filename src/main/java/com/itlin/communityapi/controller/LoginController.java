package com.itlin.communityapi.controller;

import com.itlin.communityapi.common.aop.LogAnnotation;
import com.itlin.communityapi.service.LoginService;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param loginParam
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "登录" , operator = "登录操作")
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);

    }

}
