package com.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.from.UserUpdateIsD;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
public interface IUserService extends IService<User> {
    IPage<User> pageC (IPage<User> page);
    void removeByNo (Integer[] no);
    Boolean myUpdate (Integer id,String name,Integer age,String phone,Integer sex);
    void updateIsDisabled(User user);
}
