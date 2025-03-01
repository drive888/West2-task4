package com.itlin.communityapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itlin.communityapi.dao.pojo.Collect;
import com.itlin.communityapi.dao.pojo.Goods;
import com.itlin.communityapi.dao.pojo.User;
import com.itlin.communityapi.service.GoodsService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * 点赞
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result save(@RequestBody Goods goods){
        User user = UserThreadLocal.get();
        goods.setUserId(user.getId());
        goodsService.save(goods);
        return Result.success();

    }

    /**
     * 取消点赞
     * @param goods
     * @return
     */

    @PostMapping("/delete")
    public Result delete(@RequestBody Goods goods){
        User user = UserThreadLocal.get();
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getArticleId,goods.getArticleId());
        queryWrapper.eq(Goods::getUserId,user.getId());
        goodsService.remove(queryWrapper);
        return Result.fail(600,"取消点赞成功");

    }

}
