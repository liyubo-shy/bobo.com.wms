package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Goodstype;
import com.wms.service.impl.GoodsServiceImpl;
import com.wms.service.impl.GoodstypeServiceImpl;
import io.swagger.models.auth.In;
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
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsServiceImpl goodsService;

    @GetMapping("/list")
    public List<Goods> list() {
        return goodsService.list();
    }

    //新增或更新
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        goods.setIsDisabled(0);
        return goodsService.saveOrUpdate(goods) ? Result.scu() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return goodsService.removeById(id);
    }


    //更新 d
    @PostMapping("/update")
    public boolean update(@RequestBody Goods goods) {
        return goodsService.updateById(goods);
    }


    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<Goods> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());
        //模糊查询by name
        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        goodsLambdaQueryWrapper.like(Goods::getIsDisabled,param.get("isDisabled").toString());
        goodsLambdaQueryWrapper.like(Goods::getName, param.get("name").toString());
        //storage不为空则精确查询仓库
        if (!param.get("storage").toString().equals("")) {
            goodsLambdaQueryWrapper.eq(Goods::getStorage, param.get("storage").toString());
        }
        //goodstype不为空则精确查询类型
        if (!param.get("goodstype").toString().equals("")){
            goodsLambdaQueryWrapper.eq(Goods::getGoodstype, param.get("goodstype").toString());
        }
        goodsLambdaQueryWrapper.orderByDesc(true,Goods :: getCreateDate);
        IPage<Goods> result = goodsService.page(page, goodsLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }

    //查询类型是否存在
    @GetMapping("/findByName")
    public Result findByNo(@RequestParam String name) {
        List<Goods> list = goodsService.lambdaQuery().eq(Goods::getName, name).list();
        return list.size() > 0 ? Result.scu(list) : Result.fail();
    }

    //批量删除by no
    @PostMapping("/deleteByNoMul")
    public void deleteByNoMul(@RequestBody Integer[] ids) {
        for (Integer id : ids){
            goodsService.removeById(id);
        }
    }

    //禁用
    @PostMapping("/updateIsDisabled")
    public void updateIsDisabled(@RequestBody Goods goods){
        goods.setIsDisabled(1);
        goodsService.updateById(goods);
    }

}
