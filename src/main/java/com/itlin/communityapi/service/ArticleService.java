package com.itlin.communityapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itlin.communityapi.dao.pojo.Article;
import com.itlin.communityapi.vo.ArticleVo;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.ArticleParam;
import com.itlin.communityapi.vo.params.PageParams;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    List<ArticleVo> listArticle(PageParams pageParams);

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    Result hotArticle(int limit);
    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 查看闻蒸汽详情
     *
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
    /**
     * 查询当前用户发布的文章
     */
    List<Article> getPublishedArticles(Long id);

    /**
     * 查询当前用户点赞的文章
     * @param userId
     * @return
     */
    List<Article> getLikedArticles(Long userId);
}
