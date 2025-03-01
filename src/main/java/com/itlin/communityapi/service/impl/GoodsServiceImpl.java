package com.itlin.communityapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlin.communityapi.dao.mapper.CollectMapper;
import com.itlin.communityapi.dao.mapper.GoodsMapper;
import com.itlin.communityapi.dao.pojo.Collect;
import com.itlin.communityapi.dao.pojo.Goods;
import com.itlin.communityapi.service.CollectService;
import com.itlin.communityapi.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
