package com.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> pageC (IPage<User> page);
    void removeByNo();
    void myUpdate(String name,Integer age,String phone,Integer sex);


}
