package com.itlin.communityapi.controller;

import com.itlin.communityapi.service.LoginService;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    /**
     * 登出
     * @param token
     * @return
     */
    @PostMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);

    }
}
