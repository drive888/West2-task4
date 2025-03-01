package com.itlin.communityapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.LoginService;
import com.itlin.communityapi.service.UserService;

import com.itlin.communityapi.util.JWTUtils;
import com.itlin.communityapi.vo.ErrorCode;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {


    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate<String ,String> redisTemplate;
    private static final String salt = "mszlu!@###";

    @Override
    public Result login(LoginParam loginParam) {
        String name = loginParam.getName();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        String pwd = DigestUtils.md5Hex(password + salt);
        User user = userService.findUser(name,pwd);
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public User checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectsMap = JWTUtils.checkToken(token);
        if(stringObjectsMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);

        return user;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        //1.判断参数是否合法
        //2.判断账户是否存在
        //3.不存在，注册账户
        //4.生成token
        //5.存入redis 返回
        //6.加上事务管理
        String name = loginParam.getName();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(name)
                || StringUtils.isBlank(password)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        User User = this.userService.findUserByName(name);
        if (User != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        User user = new User();
        User = new User();
        User.setName(name);
        User.setPassword(DigestUtils.md5Hex(password+salt));
        User.setRegisterTime(System.currentTimeMillis());
        User.setAvatar("/static/img/logo.b3a48c0.png");
        User.setAdmin(1); //1 为true
        User.setDeleted(0); // 0 为false
        userService.save(User);
        //token
        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
