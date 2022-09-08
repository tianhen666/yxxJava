package com.lechi.yxx.service;

import com.lechi.yxx.model.EnrollForm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lechi.yxx.vo.EnrollFormVo;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 报名表单 服务类
 * </p>
 *
 * @author zf
 * @since 2022-07-27
 */
public interface IEnrollFormService extends IService<EnrollForm> {

    List<EnrollForm> getlist(@ApiParam("门诊id") @Param("storeId") Integer storeId);

    EnrollFormVo getinfo (Integer id,Integer productId);

}
