package com.itlin.communityapi.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String password;

    private Integer admin;

    private String avatar;

    private Long registerTime;

    private Integer deleted;

    private String info;



}
