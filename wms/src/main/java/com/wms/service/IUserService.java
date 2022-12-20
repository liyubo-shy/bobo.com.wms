package com.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
