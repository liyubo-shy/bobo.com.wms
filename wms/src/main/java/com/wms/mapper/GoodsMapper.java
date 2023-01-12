package com.wms.mapper;

import com.wms.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-12-13
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    void updateIsDisabled (@Param("id") int id);
    List<Goods> analysis ();
}
