package com.lechi.yxx.service;

import com.lechi.yxx.model.StoreProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 店铺爆品 服务类
 * </p>
 *
 * @author zf
 * @since 2022-07-29
 */
public interface IStoreProductService extends IService<StoreProduct> {

    List<StoreProduct> getlist(Integer storeId, Integer pageIndex);

}
