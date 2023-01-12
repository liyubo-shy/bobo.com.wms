package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Storage;
import com.wms.entity.User;
import com.wms.service.impl.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageServiceImpl storageService;

    @GetMapping("/list")
    public List<Storage> list (){
        return storageService.list();
    }
    //新增或更新
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        return storageService.saveOrUpdate(storage) ? Result.scu() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return storageService.removeById(id);
    }


    //更新
    @PostMapping("/update")
    public boolean update(@RequestBody Storage storage) {
        return storageService.updateById(storage);
    }


    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<Storage> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());
        //模糊查询by name
        LambdaQueryWrapper<Storage> storageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        storageLambdaQueryWrapper.like(Storage::getName, param.get("name").toString());
        storageLambdaQueryWrapper.orderByDesc(true,Storage::getCreateDate);
        IPage<Storage> result = storageService.page(page, storageLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }

    //查询仓库是否存在
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        List<Storage> list = storageService.lambdaQuery().eq(Storage::getName, name).list();
        return list.size() > 0 ? Result.scu(list) : Result.fail();
    }

    //批量删除by no
    @PostMapping("/deleteByNoMul")
    public void deleteByNoMul(@RequestBody Integer[] ids) {
        for (Integer id : ids){
            storageService.removeById(id);
        }
    }

    @GetMapping("/analysis")
    public List<Storage> analysis (){
        return storageService.analysis();
    }
}
