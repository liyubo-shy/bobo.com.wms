package com.wms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Record;
import com.wms.mapper.RecordMapper;
import com.wms.service.IRecordService;
import com.wms.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: bo
 * @DATE: 2022/11/4 8:54
 **/
@SpringBootTest
public class LambadaTest {

    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private RecordMapper recordMapper;
    @Test
    public void test(){
        List<Record> recordIPage = recordService.listRecord(1, 2, 2);
        System.out.println("wahahaa"+recordIPage.size());
        Record record = recordMapper.selectById(1);
        System.out.println("num"+record.getCount());
    }
}
