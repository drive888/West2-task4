package com.itlin.communityapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itlin.communityapi.dao.mapper.UserMapper;
import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.LoginService;
import com.itlin.communityapi.service.UserService;
import com.itlin.communityapi.util.JWTUtils;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.ErrorCode;
import com.itlin.communityapi.vo.LoginUserVo;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.UserVo;
import com.itlin.communityapi.vo.params.UserParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String ,String> redisTemplate;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    @Override
    public User findUser(String name, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,name);
        queryWrapper.eq(User::getPassword,password);
        queryWrapper.select(User::getName,User::getId,User::getAvatar);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findUserById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            user = new User();
            user.setName("Leo");

        }

        return user;

    }
    @Override
    public UserVo findUserVoById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            user = new User();
            user.setId(1L);
            user.setAvatar("/static/img/logo.b3a48c0.png");
            user.setName("Leo");

        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user , userVo);

        return userVo;

    }

    @Override
    public User edit(UserParams userParams) {
        User userPojo = UserThreadLocal.get();
        userPojo.setAvatar(userParams.getAvatar());
        userPojo.setName(userParams.getName());
        userPojo.setPassword(userParams.getPassword());
        userMapper.updateById(userPojo);
        return userPojo;
    }


    @Override
    public Result findUserByToken(String token) {
        //1.token合法性校验(是否为空，解析是否成功，redis是否存在)
        //2.如果校验失败 返回错误
        //3.如果成功，返回对应的结果 LoginUserVo
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        User user = loginService.checkToken(token);
        if(user == null){
            Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());

        }

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(user.getId());
        loginUserVo.setName(user.getName());
        loginUserVo.setAvatar(user.getAvatar());
        return Result.success(loginUserVo);
    }

    @Override
    public User findUserByName(String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName,name);
        queryWrapper.last("limit 1");
        this.userMapper.selectOne(queryWrapper);
        return null;
    }

    @Override
    public User save(User user) {
        this.userMapper.insert(user);

        return user;
    }
}
