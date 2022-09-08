package com.lechi.yxx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lechi.yxx.model.StoreProduct;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 店铺爆品 Mapper 接口
 * </p>
 *
 * @author zf
 * @since 2022-07-29
 */
public interface StoreProductMapper extends BaseMapper<StoreProduct> {

    @Select("select id,store_id,title,pics,price,price_normal from store_produce where store_id = ${storeId} and status=1")
    List<StoreProduct> getlist(Integer storeId,Integer pageIndex);

}
