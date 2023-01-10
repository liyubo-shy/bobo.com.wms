package com.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
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
    List<Record> listRecord (String goods, String userId, String adminId, String goodstype, String storage, int pageNum, int pageSize, int inOrOut, String startDate,String endDate);

}
