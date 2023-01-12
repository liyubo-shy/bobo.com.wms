package com.wms.mapper;

import com.wms.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
@Mapper
@Repository
public interface StorageMapper extends BaseMapper<Storage> {
    List<Storage> analysis ();
}
