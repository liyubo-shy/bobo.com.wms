package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goodstype;
import com.wms.service.impl.GoodstypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private GoodstypeServiceImpl goodstypeService;

    @GetMapping("/list")
    public List<Goodstype> list (){
        return goodstypeService.list();
    }

    //新增或更新dadae
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {
        return goodstypeService.saveOrUpdate(goodstype) ? Result.scu() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return goodstypeService.removeById(id);
    }


    //更新
    @PostMapping("/update")
    public boolean update(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype);
    }


    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<Goodstype> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());
        //模糊查询by name
        LambdaQueryWrapper<Goodstype> goodstypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        goodstypeLambdaQueryWrapper.like(Goodstype::getName, param.get("name").toString());
        goodstypeLambdaQueryWrapper.orderByDesc(true,Goodstype::getCreateDate);
        IPage<Goodstype> result = goodstypeService.page(page, goodstypeLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }
    //查询类型是否存在
    @GetMapping("/findByName")
    public Result findByNo(@RequestParam String name) {
        List<Goodstype> list = goodstypeService.lambdaQuery().eq(Goodstype::getName, name).list();
        return list.size() > 0 ? Result.scu(list) : Result.fail();
    }

    //批量删除by no
    @PostMapping("/deleteByNoMul")
    public void deleteByNoMul(@RequestBody Integer[] ids) {
        for (Integer id : ids){
            goodstypeService.removeById(id);
        }
    }
}
