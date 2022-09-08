package com.lechi.yxx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lechi.yxx.model.StoreProductOrder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zf
 * @since 2022-08-08
 */
public interface IStoreProductOrderService extends IService<StoreProductOrder> {

    String uploadFile(MultipartFile uploadFile, HttpServletRequest request);

}
