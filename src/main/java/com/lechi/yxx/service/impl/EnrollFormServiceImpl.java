package com.lechi.yxx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lechi.yxx.mapper.EnrollFormMapper;
import com.lechi.yxx.mapper.StoreProductMapper;
import com.lechi.yxx.model.EnrollForm;
import com.lechi.yxx.model.StoreProduct;
import com.lechi.yxx.service.IEnrollFormService;
import com.lechi.yxx.vo.EnrollFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 报名表单 服务实现类
 * </p>
 *
 * @author zf
 * @since 2022-07-27
 */
@Service
public class EnrollFormServiceImpl extends ServiceImpl<EnrollFormMapper, EnrollForm> implements IEnrollFormService {

    @Autowired
    private EnrollFormMapper enrollFormMapper;
    @Autowired
    private StoreProductMapper storeProductMapper;

    @Override
    public List<EnrollForm> getlist(Integer storeId) {
        return enrollFormMapper.getlist(storeId);
    }

    @Override
    public EnrollFormVo getinfo(Integer id, Integer productId) {
        EnrollForm enrollForm = enrollFormMapper.selectById(id);
        StoreProduct storeProduct = storeProductMapper.selectById(productId);
        EnrollFormVo enrollFormVo = new EnrollFormVo();
        enrollFormVo.setEnrollForm(enrollForm);
        enrollFormVo.setStoreProduct(storeProduct);
        return enrollFormVo;
    }

}
