package com.itlin.communityapi.service;

import com.itlin.communityapi.vo.Result;
import com.itlin.communityapi.vo.params.CommentParam;

public interface CommentsService {
    /**
     *根据文章id查询所有文章列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 评论功能
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
