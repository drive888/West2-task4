package com.itlin.communityapi.service;

import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.UserVo;
import com.itlin.communityapi.vo.params.UserParams;

public interface UserService  {

    User findUser(String name, String password);

    User findUserById(Long id);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 根据id获取UserVo对象
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);

    /**
     * 修改用户信息
     * @param userParams
     * @return
     */
    User edit(UserParams userParams);
}
