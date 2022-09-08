package com.lechi.yxx.service.impl;

import com.lechi.yxx.model.Banner;
import com.lechi.yxx.mapper.BannerMapper;
import com.lechi.yxx.service.IBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-07-26
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    public String uploadFile(MultipartFile uploadFile, HttpServletRequest request) {
        File folder = new File("D:\\NewsWebSite\\images\\tooth\\store\\");
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            // 文件保存
            uploadFile.transferTo(new File(folder, newName));

            // 返回上传文件的访问路径
            String filePath = "images" + newName;
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("错误");
        }
    }
}
