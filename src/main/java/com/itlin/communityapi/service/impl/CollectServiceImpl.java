package com.itlin.communityapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itlin.communityapi.dao.mapper.CollectMapper;
import com.itlin.communityapi.dao.pojo.Collect;
import com.itlin.communityapi.service.CollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper , Collect> implements CollectService {
}
