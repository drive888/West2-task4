package com.itlin.communityapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlin.communityapi.dao.mapper.ArticleBodyMapper;
import com.itlin.communityapi.dao.mapper.ArticleMapper;
import com.itlin.communityapi.dao.mapper.GoodsMapper;
import com.itlin.communityapi.dao.pojo.*;
import com.itlin.communityapi.service.ArticleService;
import com.itlin.communityapi.service.UserService;
import com.itlin.communityapi.util.UserThreadLocal;
import com.itlin.communityapi.vo.ArticleBodyVo;
import com.itlin.communityapi.vo.ArticleVo;
import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.ArticleParam;
import com.itlin.communityapi.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserService userService;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        //分页查询Article数据库表
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPagesize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //按照创建时间进行排序
        queryWrapper.orderByDesc(Article::getCreateTime);
        //按照点击量进行排序
        queryWrapper.orderByDesc(Article::getViewCounts);
        Page<Article> articlePage= articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records,true);
        return articleVoList;


    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateTime);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false));
    }

    @Override
    public Result findArticleById(Long articleId) {
        //1.根据id查文章信息
        //2.根据bodyId 做关联查询
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article,true,true );
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        //将接口加入到登录拦截器中，从而获取当前登录的用户信息
        User user = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(user.getId());
        article.setCreateTime(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        articleMapper.insert(article);
        //body文章内容
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);


        ArticleVo articleVo = new ArticleVo();
        articleMapper.updateById(article);
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    @Override
    public List<Article> getPublishedArticles(Long authorId) {
        return list(new LambdaQueryWrapper<Article>().eq(Article::getAuthorId, authorId));


    }

    @Override
    public List<Article> getLikedArticles(Long userId) {
        List<Goods> goods = goodsMapper.selectList(new LambdaQueryWrapper<Goods>().eq(Goods::getUserId, userId));
        List<Long> articleIds = goods.stream().map(Goods::getArticleId).collect(Collectors.toList());
        if (articleIds.isEmpty()) {
            return new ArrayList<>(); // 如果没有点赞的文章，直接返回空列表
        }
        return articleMapper.selectBatchIds(articleIds);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,false));
        }


        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isBody));
        }


        return articleVoList;
    }

    private ArticleVo copy(Article article , boolean isAuthor,boolean isBody){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article ,articleVo);
        articleVo.setCreateTime(new DateTime(article.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.findUserById(authorId).getName());

        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));

        }
        return articleVo;
    }
    @Autowired
    ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


}
