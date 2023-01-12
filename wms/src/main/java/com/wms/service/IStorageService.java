package com.wms.service;

import com.wms.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
public interface IStorageService extends IService<Storage> {
    List<Storage> analysis ();
}
