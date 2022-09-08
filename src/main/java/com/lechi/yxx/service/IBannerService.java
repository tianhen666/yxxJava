package com.lechi.yxx.service;

import com.lechi.yxx.model.Banner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
public interface IBannerService extends IService<Banner> {

    String uploadFile(MultipartFile uploadFile, HttpServletRequest request);

}
