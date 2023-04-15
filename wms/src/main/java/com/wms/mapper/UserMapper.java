package com.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.vo.UserAgeAnalysisVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@Mapper
@Repository

public interface UserMapper extends BaseMapper<User> {
    IPage<User> pageC (IPage<User> page);
    void removeByNo();
    void myUpdate(String name,Integer age,String phone,Integer sex);
    void updateIsDisabled(Integer id);
    List<UserAgeAnalysisVo> userAgeAnalysis ();
    List<User> findSaltIsNull();
    void updatePasswordAndSalt(@Param("id") int id, @Param("salt") String salt, @Param("password") String password);


}
