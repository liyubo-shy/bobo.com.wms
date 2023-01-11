package com.wms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Record;
import com.wms.mapper.RecordMapper;
import com.wms.service.IRecordService;
import com.wms.service.impl.RecordServiceImpl;
import io.swagger.models.auth.In;
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
    public static void main(String[] args) {
        Integer a1 = 100;
        Integer a2 = 100;
        System.out.println(a1 == a2);

        Integer b1 = 200;
        Integer b2 = 200;
        System.out.println(b1 == b2);
    }
}
