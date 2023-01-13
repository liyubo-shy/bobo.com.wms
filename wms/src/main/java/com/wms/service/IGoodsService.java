package com.wms.service;

import com.wms.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.vo.GoodsAnalysisVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-12-13
 */
public interface IGoodsService extends IService<Goods> {
    void updateIsDisabled(int id);
    List<GoodsAnalysisVo> analysis();
}
