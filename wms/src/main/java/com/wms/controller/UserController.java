package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.impl.MenuServiceImpl;
import com.wms.service.impl.UserServiceImpl;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MenuServiceImpl menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    //查询账号是否存在
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List<User> list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.scu(list) : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.saveOrUpdate(user) ? Result.scu() : Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List<User> userList = userService.lambdaQuery().eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();
        if (userList.size() > 0) {
            User user1 = userList.get(0);
            List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu", menuList);
            return Result.scu(res);
        } else {
            return Result.fail();
        }
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //修改
    @PostMapping("/update")
    public Boolean update(@RequestBody User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        updateWrapper.set("name",user.getName());
        updateWrapper.set("age",user.getAge());
        updateWrapper.set("phone",user.getPhone());
        updateWrapper.set("sex",user.getSex());
        userService.update(new User(),updateWrapper);
        return true;

    }

    //修改或新增
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //模糊查询 by name
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName, user.getName());
        return Result.scu(userService.list(userLambdaQueryWrapper));
    }

    //分页查询
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<User> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());
        //模糊查询by name
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName, param.get("name").toString());

        IPage<User> result = userService.page(page, userLambdaQueryWrapper);

        System.out.println("总计：" + result.getTotal() + "条");
        return result.getRecords();

    }

    //分页查询,返回数据封装
    @PostMapping("/listPage1")
    public Result listPage1(@RequestBody QueryPageParam query) {
        //拿到参数的HashMap集合
        HashMap param = query.getParam();
        //分页
        Page<User> page = new Page<>();
        //第几页
        page.setCurrent(query.getPageNum());
        //每页数量
        page.setSize(query.getPageSize());
        //模糊查询by name
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName, param.get("name").toString());
        //精确查询by sex
        userLambdaQueryWrapper.like(User::getSex, param.get("sex").toString());
        //精确查询by roleId
        userLambdaQueryWrapper.like(User::getRoleId, param.get("roleId").toString());
        //模糊查询by no
        userLambdaQueryWrapper.like(User::getNo, param.get("no").toString());

        IPage<User> result = userService.page(page, userLambdaQueryWrapper);
        return Result.scu(result.getRecords(), result.getTotal());

    }

    //批量删除by no
    @PostMapping("/deleteByNoMul")
    public void deleteByNoMul(@RequestBody Integer[] ids) {
        for (Integer id : ids){
            userService.removeById(id);
        }
    }
}
