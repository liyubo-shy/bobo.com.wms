package com.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.resource.StringResource;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.from.UserUpdateIsD;
import com.wms.mapper.UserMapper;
import com.wms.service.IMenuService;
import com.wms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.utils.RedisConstants;
import com.wms.vo.UserAgeAnalysisVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-11-03
 */
@Service
@Transactional

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IMenuService menuService;
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
    public Boolean myUpdate(Integer id, String name, Integer age, String phone, Integer sex) {
        return true;
    }

    @Override
    public void updateIsDisabled(User user) {
        userMapper.updateIsDisabled(user.getId());
    }

    @Override
    public List<UserAgeAnalysisVo> userAgeAnalysis() {
        return userMapper.userAgeAnalysis();
    }

    @Override
    public Result login1(User user) {
        LambdaQueryChainWrapper<User> saltQuery = lambdaQuery().select(User::getSalt).eq(User::getNo, user.getNo());
        List<User> saltList = saltQuery.list();
        List<User> userList;
        if (saltList.get(0) == null) {
            System.out.println("未加密账号");
            userList = lambdaQuery().eq(User::getNo, user.getNo())
                    .eq(User::getPassword, user.getPassword()).eq(User::getIsDisabled, 0).list();
        } else {
            System.out.println("加密账号");
            String salt = saltList.get(0).getSalt();
            System.out.println("加密盐：" + salt);
            userList = lambdaQuery().eq(User::getNo, user.getNo())
                    .eq(User::getPassword, SecureUtil.md5(user.getPassword() + salt)).eq(User::getIsDisabled, 0).list();
        }


        if (userList.size() > 0) {
            User user1 = userList.get(0);
            List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            String token = UUID.randomUUID().toString(true);
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu", menuList);
            res.put("token", token);
            Map<String, Object> userMap = BeanUtil.beanToMap(user1,
                    new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString())
            );
            stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token, userMap);
            stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
            return Result.scu(res);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result clearToken(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        System.out.println("退出登录，删除token:" + token);
        stringRedisTemplate.delete(RedisConstants.LOGIN_USER_KEY + token);
        return Result.scu();
    }
}
