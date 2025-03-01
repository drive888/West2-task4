package com.itlin.communityapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itlin.communityapi.common.aop.LogAnnotation;
import com.itlin.communityapi.dao.pojo.Collect;
import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.CollectService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    CollectService collectService;

    /**
     * 新增收藏
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result save(@RequestBody Collect collect){
        User user = UserThreadLocal.get();
        collect.setUserId(user.getId());
        collectService.save(collect);
        return Result.success();

    }

    /**
     * 取消收藏
     * @param collect
     * @return
     */

    @PostMapping("/delete")
    public Result delete(@RequestBody Collect collect){
        User user = UserThreadLocal.get();
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getArticleId,collect.getArticleId());
        queryWrapper.eq(Collect::getUserId,user.getId());
        collectService.remove(queryWrapper);
        return Result.fail(600,"取消收藏成功");

    }

}
