package com.wms.service.impl;

import com.wms.entity.Storage;
import com.wms.mapper.StorageMapper;
import com.wms.service.IStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2022-12-06
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public List<Storage> analysis (){
        return storageMapper.analysis();
    }
}
