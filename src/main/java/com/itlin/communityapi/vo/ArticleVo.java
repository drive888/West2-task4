package com.itlin.communityapi.vo;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class ArticleVo {
    private Long id;

    private String title;

    private Integer commentCounts;

    private Integer viewCounts;

    private String author;

    private ArticleBodyVo body;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 点赞数
     */
    private Integer goodsCounts;
    /**
     * 收藏数
     */
    private Integer collectionCounts;
}
