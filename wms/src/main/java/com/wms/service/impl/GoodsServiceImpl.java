package com.wms.service.impl;

import com.wms.entity.Goods;
import com.wms.mapper.GoodsMapper;
import com.wms.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.vo.GoodsAnalysisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-12-13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void updateIsDisabled(int id) {
        goodsMapper.updateIsDisabled(id);
    }

    @Override
    public List<GoodsAnalysisVo> analysis() {
        return goodsMapper.analysis();
    }
}
