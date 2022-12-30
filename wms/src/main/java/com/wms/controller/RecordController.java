package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Record;
import com.wms.service.impl.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


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
        //模糊查询by username
//        LambdaQueryWrapper<Record> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        userLambdaQueryWrapper.like(Record::getName, param.get("name").toString());
//        //精确查询by sex
//        userLambdaQueryWrapper.like(User::getSex, param.get("sex").toString());
//        //精确查询by roleId
//        userLambdaQueryWrapper.like(User::getRoleId, param.get("roleId").toString());
//        //模糊查询by no
//        userLambdaQueryWrapper.like(User::getNo, param.get("no").toString());
//        userLambdaQueryWrapper.orderByDesc(true,User::getCreateDate);
        IPage<Record> result = recordService.page(page, recordLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }

    @PostMapping("/test")
    public Result test(@RequestBody QueryPageParam queryPageParam) {
        HashMap param = queryPageParam.getParam();
        List<Record> records = recordService.listRecord(Integer.parseInt(param.get("goods").toString()), Integer.parseInt(param.get("userId").toString()), Integer.parseInt(param.get("adminId").toString()));
        System.out.println(records.size());
        String size_string = records.size() + "";
        long size = Long.parseLong(size_string);
        return Result.scu(records, size);
    }
}
