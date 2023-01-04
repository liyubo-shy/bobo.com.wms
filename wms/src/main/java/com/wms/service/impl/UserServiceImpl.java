package com.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.User;
import com.wms.from.UserUpdateIsD;
import com.wms.mapper.UserMapper;
import com.wms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@Service
@Transactional

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper userMapper;
    @Override
    public IPage<User> pageC(IPage<User> page) {
        userMapper.pageC(page);
        return null;
    }


    @Override
    public void removeByNo(Integer[] id) {
    }

    @Override
    public Boolean myUpdate(Integer id,String name,Integer age,String phone,Integer sex) {
        return true;
    }

    @Override
    public void updateIsDisabled(User user) {
        userMapper.updateIsDisabled(user.getId());
    }
}
