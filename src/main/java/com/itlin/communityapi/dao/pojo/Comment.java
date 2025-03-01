package com.itlin.communityapi.dao.pojo;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long commentatorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}