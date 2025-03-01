package com.itlin.communityapi.controller;

import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.UserService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.UserVo;
import com.itlin.communityapi.vo.params.UserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取用户数据
     * @param token
     * @return
     */
    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
        //报空指针异常

    }
    /**
     * 修改用户信息
     */
    //修改用户信息
    @PutMapping("/edit")
    public Result edit(@RequestBody UserParams userParams){
        User userNew = userService.edit(userParams);
        return Result.success(userNew);



    }


}
