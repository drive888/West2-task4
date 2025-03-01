package com.itlin.communityapi.vo.params;

import lombok.Data;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private String summary;

    private String title;
}