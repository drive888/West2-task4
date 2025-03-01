package com.itlin.communityapi.controller;

import com.itlin.communityapi.common.aop.LogAnnotation;
import com.itlin.communityapi.service.ArticleService;
import com.itlin.communityapi.vo.ArticleVo;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.ArticleParam;
import com.itlin.communityapi.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//json数据进行交互
@RestController
@RequestMapping("/articles")
public class ArticleController {


    // get 获取数据  // post 提交数据  // put修改数据   //delete 删除数据
    @Autowired
    ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @GetMapping("/list")
    @LogAnnotation(module = "文章" , operator = "获取文章列表")
    public Result listArticle(PageParams pageParams){
        List<ArticleVo> articleVoList = articleService.listArticle(pageParams);
        return Result.success(articleVoList);

    }
    //接口测试异常

    /**
     * 最热文章
     * @return
     */

    @PostMapping("/hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);

    }
    /**
     * 最新文章
     * @return
     */

    @PostMapping("/new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);

    }

    /**
     * 文章详情
     * @param
     * @return
     */

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
