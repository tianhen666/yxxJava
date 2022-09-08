package com.lechi.yxx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lechi.yxx.model.StoreWxapp;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zf
 * @since 2022-08-02
 */
public interface StoreWxappMapper extends BaseMapper<StoreWxapp> {

    @Select("select * from store_wxapp where app_id = ${AppId}")
    Integer selectByAppId(String AppId);

}
