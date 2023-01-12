package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Record;
import com.wms.service.impl.GoodsServiceImpl;
import com.wms.service.impl.RecordServiceImpl;
import com.wms.vo.RecordAnalysisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bobo
 * @since 2022-12-23
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordServiceImpl recordService;
    @Autowired
    private GoodsServiceImpl goodsService;

    @GetMapping("/list")
    public List<Record> list() {
        return recordService.list();
    }

    //分页查询,返回数据封装
    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<Record> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());

        //精确查询by goods
        LambdaQueryWrapper<Record> recordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if ((param.get("goods") != null)) {
            if (param.get("goods").toString().equals("")) {
                recordLambdaQueryWrapper.like(Record::getGoods, param.get("goods").toString());
            } else {
                recordLambdaQueryWrapper.eq(Record::getGoods, param.get("goods").toString());

            }

        }

        IPage<Record> result = recordService.page(page, recordLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }

    @PostMapping("/listPage2")
    public Result listPage2(@RequestBody QueryPageParam queryPageParam) {
        HashMap param = queryPageParam.getParam();
        int pageSize = queryPageParam.getPageSize();
        int pageNum = queryPageParam.getPageNum();
        pageNum = (pageNum - 1) * pageSize;

        String goodstype = "";
        String storage = "";
        String startDate = "";
        String endDate = "";

        int inOrOut = 3;
        if (param.get("goodstype") != null) {
            goodstype = param.get("goodstype").toString();
        }
        if (param.get("storage") != null) {
            storage = param.get("storage").toString();
        }
        if (param.get("inOrOut") != null) {
            if (!param.get("inOrOut").toString().equals("")) {
                inOrOut = Integer.parseInt(param.get("inOrOut").toString());
            }
        }
        if (param.get("startDate") != null) {
            startDate = param.get("startDate").toString();
        }
        if (param.get("endDate") != null) {
            endDate = param.get("endDate").toString();
        }

        List<Record> records = recordService.listRecord(
                param.get("goods").toString(),
                param.get("userId").toString(),
                param.get("adminId").toString(),
                goodstype,
                storage,
                pageNum,
                pageSize,
                inOrOut,
                startDate,
                endDate
        );
        System.out.println(records.size());
        String size_string = records.size() + "";
        long size = Long.parseLong(size_string);
        return Result.scu(records, size);
    }

    @PostMapping("/exportRecord")
    public Result exportRecord(@RequestBody QueryPageParam queryPageParam) {
        HashMap param = queryPageParam.getParam();
        int pageSize = queryPageParam.getPageSize();
        int pageNum = queryPageParam.getPageNum();
        pageNum = (pageNum - 1) * pageSize;

        String goodstype = "";
        String storage = "";
        String startDate = "";
        String endDate = "";

        int inOrOut = 3;
        if (param.get("goodstype") != null) {
            goodstype = param.get("goodstype").toString();
        }
        if (param.get("storage") != null) {
            storage = param.get("storage").toString();
        }
        if (param.get("inOrOut") != null) {
            if (!param.get("inOrOut").toString().equals("")) {
                inOrOut = Integer.parseInt(param.get("inOrOut").toString());
            }
        }
        if (param.get("startDate") != null) {
            startDate = param.get("startDate").toString();
        }
        if (param.get("endDate") != null) {
            endDate = param.get("startDate").toString();
        }

        List<Record> records = recordService.exportRecord(
                param.get("goods").toString(),
                param.get("userId").toString(),
                param.get("adminId").toString(),
                goodstype,
                storage,
                pageNum,
                pageSize,
                inOrOut,
                startDate,
                endDate
        );
        System.out.println(records.size());
        String size_string = records.size() + "";
        long size = Long.parseLong(size_string);
        return Result.scu(records, size);
    }
    @PostMapping("/saveIn")
    public Result saveIn(@RequestBody Record record) {
        Lock lock = new ReentrantLock();
        lock.lock();
        Goods goods = goodsService.getById(record.getGoods());
        goods.setCount(goods.getCount() + record.getCount());
        boolean updateByIdResult = goodsService.updateById(goods);
        lock.unlock();
        return recordService.save(record) && updateByIdResult ? Result.scu() : Result.fail();
    }

    @PostMapping("/saveOut")
    public Result saveOut(@RequestBody Record record) {
        Lock lock = new ReentrantLock();
        lock.lock();
        record.setCount(Integer.parseInt("-" + record.getCount()));
        Goods goods = goodsService.getById(record.getGoods());
        goods.setCount(goods.getCount() + record.getCount());
        boolean updateByIdResult = goodsService.updateById(goods);
        lock.unlock();
        return recordService.save(record) && updateByIdResult ? Result.scu() : Result.fail();
    }

    @GetMapping("/recordInAnalysis")
    public List<Record> recordInAnalysis(){
        return recordService.recordInAnalysis();
    }
    @GetMapping("/recordOutAnalysis")
    public List<Record> recordOutAnalysis(){
        return recordService.recordOutAnalysis();
    }
}
