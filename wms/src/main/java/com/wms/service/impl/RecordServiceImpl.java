package com.wms.service.impl;

import com.wms.entity.Record;
import com.wms.mapper.RecordMapper;
import com.wms.service.IRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.vo.ExportRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> listRecord(String goods, String userId, String adminId, String goodstype, String storage, int pageNum, int pageSize, int inOrOut, String startDate, String endDate) {

        return recordMapper.listRecord(
                goods,
                userId,
                adminId,
                goodstype,
                storage,
                pageNum,
                pageSize,
                inOrOut,
                startDate,
                endDate);
    }
    public List<Record> exportRecord(String goods, String userId, String adminId, String goodstype, String storage, int pageNum, int pageSize, int inOrOut, String startDate, String endDate) {
        return recordMapper.exportRecord(
                goods,
                userId,
                adminId,
                goodstype,
                storage,
                pageNum,
                pageSize,
                inOrOut,
                startDate,
                endDate);
    }
}
