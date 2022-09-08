package com.lechi.yxx.service;

import com.lechi.yxx.model.Store;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 店铺表 服务类
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
public interface IStoreService extends IService<Store> {

    Store getone(@ApiParam("门诊id") @Param("id") Integer id);

    Store getinfo(Integer id);

}
