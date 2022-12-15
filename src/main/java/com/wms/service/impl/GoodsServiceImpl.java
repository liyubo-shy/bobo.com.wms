package com.wms.service.impl;

import com.wms.entity.Goods;
import com.wms.mapper.GoodsMapper;
import com.wms.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
