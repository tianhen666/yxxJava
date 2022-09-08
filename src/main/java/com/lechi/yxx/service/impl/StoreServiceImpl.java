package com.lechi.yxx.service.impl;

import com.lechi.yxx.model.Store;
import com.lechi.yxx.mapper.StoreMapper;
import com.lechi.yxx.service.IStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺表 服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store getone(Integer id) {
        return storeMapper.getone(id);
    }

    @Override
    public Store getinfo(Integer id) {
        return storeMapper.getinfo(id);
    }
}
