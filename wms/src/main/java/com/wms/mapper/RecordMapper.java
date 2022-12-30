package com.wms.mapper;

import com.wms.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<Record> {
            List<Record> listRecord (@Param("goods") int goods, @Param("userId") int userId, @Param("adminId") int adminId);
}
