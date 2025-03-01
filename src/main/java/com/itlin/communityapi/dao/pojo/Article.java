package com.itlin.communityapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Article {

        private Long id;

        private String title;

        private Integer commentCounts;

        private Integer viewCounts;

        /**
         * 作者id
         */
        private Long authorId;

        /**
         * 内容id
         */
        private Long bodyId;

        /**
         * 创建时间
         */
        private Long createTime;
        /**
        * 点赞数
        */
        private Integer goodsCounts;
        /**
        * 收藏数
        */
        private Integer collectionCounts;
}


