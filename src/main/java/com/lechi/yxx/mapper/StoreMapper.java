package com.lechi.yxx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lechi.yxx.model.Store;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 店铺表 Mapper 接口
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
public interface StoreMapper extends BaseMapper<Store> {

    @Select("select store_id,user_id,name,mobile,icon,address,address_detail,business_dt,lng,lat from store where store_id = ${id} and flag = 1")
    Store getone(Integer id);

    @Select("select store_id,user_id,name,mobile,icon,pics,inner_pics,address,address_detail,business_dt,desc_data,lng,lat from store where store_id = ${id}")
    Store getinfo(Integer id);

}



