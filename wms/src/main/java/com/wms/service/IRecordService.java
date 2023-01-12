package com.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.vo.RecordVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
public interface IRecordService extends IService<Record> {
    Page<RecordVo> listRecord (
            Page<RecordVo> page,
            String goods,
            String userId,
            String adminId,
            String goodstype,
            String storage,
            int pageNum,
            int pageSize,
            int inOrOut,
            String startDate,
            String endDate);
    List<Record> exportRecord (
            String goods,
            String userId,
            String adminId,
            String goodstype,
            String storage,
            int pageNum,
            int pageSize,
            int inOrOut,
            String startDate,
            String endDate);
    List<Record> recordInAnalysis();
    List<Record> recordOutAnalysis();
}
