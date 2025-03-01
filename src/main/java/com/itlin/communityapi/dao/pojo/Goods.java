package com.itlin.communityapi.dao.pojo;

import lombok.Data;

@Data
public class Goods {
    private Long id;
    /**
     * 点赞用户id
     */
    private Long userId;
    /**
     * 被点赞文章id
     */
    private Long articleId;
}
