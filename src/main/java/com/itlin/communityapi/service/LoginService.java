package com.itlin.communityapi.service;

import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;

public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 校验Token
     * @param token
     * @return
     */
    User checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
