package com.lechi.yxx.service.impl;

import com.lechi.yxx.model.StoreProduct;
import com.lechi.yxx.mapper.StoreProductMapper;
import com.lechi.yxx.service.IStoreProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 店铺爆品 服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-07-29
 */
@Service
public class StoreProductServiceImpl extends ServiceImpl<StoreProductMapper, StoreProduct> implements IStoreProductService {

    @Autowired
    private StoreProductMapper storeProductMapper;

    @Override
    public List<StoreProduct> getlist(Integer storeId, Integer pageIndex) {
        return storeProductMapper.getlist(storeId,pageIndex);
    }
}
