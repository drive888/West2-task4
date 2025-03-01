package com.itlin.communityapi.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;
import lombok.Data;
import com.itlin.communityapi.dao.pojo.User;
import lombok.ToString;

import java.util.List;

@Data
public class CommentVo  {
    //防止前端将id转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo commentator;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}