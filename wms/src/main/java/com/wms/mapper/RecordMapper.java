package com.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.vo.RecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<Record> {
    Page<RecordVo> listRecord(
            @Param("page")Page<RecordVo> page,
            @Param("goods") String goods,
            @Param("userId") String userId,
            @Param("adminId") String adminId,
            @Param("goodsType") String goodsType,
            @Param("storage") String storage,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("inOrOut") int inOrOut,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    List<Record> exportRecord(
            @Param("goods") String goods,
            @Param("userId") String userId,
            @Param("adminId") String adminId,
            @Param("goodsType") String goodsType,
            @Param("storage") String storage,
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("inOrOut") int inOrOut,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
    List<Record> recordInAnalysis();
    List<Record> recordOutAnalysis();
}
