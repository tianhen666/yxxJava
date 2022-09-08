package com.lechi.yxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lechi.yxx.mapper.StoreProductOrderMapper;
import com.lechi.yxx.model.StoreProductOrder;
import com.lechi.yxx.service.IStoreProductOrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-08-08
 */
@Service
public class StoreProductOrderServiceImpl extends ServiceImpl<StoreProductOrderMapper, StoreProductOrder> implements IStoreProductOrderService {

    @Override
    public String uploadFile(MultipartFile uploadFile, HttpServletRequest request) {
        File folder = new File("D:\\NewsWebSite\\images\\tooth\\product\\");
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
            String filePath = "https://imgs.ledianduo.com/images/tooth/product/" +uploadFile+ newName;
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("错误");
        }
    }
}
