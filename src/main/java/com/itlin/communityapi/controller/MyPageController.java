package com.itlin.communityapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itlin.communityapi.dao.pojo.Article;
import com.itlin.communityapi.dao.pojo.Collect;
import com.itlin.communityapi.service.ArticleService;
import com.itlin.communityapi.service.CollectService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.ArticleVo;
import com.itlin.communityapi.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    ArticleService articleService;


    @GetMapping("/articles")
    public Object getUserArticles() {
        List<Article> publishedArticles = articleService.getPublishedArticles(UserThreadLocal.get().getId());
        return Result.success(publishedArticles);



    }
    @GetMapping("/goods")
    public Object getUserGoods() {
        List<Article> likedArticles = articleService.getLikedArticles(UserThreadLocal.get().getId());

        return Result.success(likedArticles);



    }



}
