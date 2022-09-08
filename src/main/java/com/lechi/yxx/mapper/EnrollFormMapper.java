package com.lechi.yxx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lechi.yxx.model.EnrollForm;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 报名表单 Mapper 接口
 * </p>
 *
 * @author zf
 * @since 2022-07-27
 */
public interface EnrollFormMapper extends BaseMapper<EnrollForm> {

    @Select("select user_id,store_id,title,content,start_dt,end_dt,main_pic from enroll_form where store_id = ${storeId} and status=2")
    List<EnrollForm> getlist(@ApiParam("门诊id") @Param("storeId") Integer storeId);

    @Select("select id,user_id,store_id,title,content,main_pic,imgs,post_pic,start_dt,end_dt,price,share_pic,product_id from enroll_form where id = ${id}")
    EnrollForm getinfo (@ApiParam("活动id") @Param("id") Integer id);

}
